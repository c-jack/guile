/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.controller;

import static uk.cjack.guile.utils.ConfigUtil.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import liquibase.Liquibase;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import uk.cjack.guile.config.ConfigurationLoader;
import uk.cjack.guile.config.ConnectionConfig;
import uk.cjack.guile.model.Config;

@Component
public class LiquibaseRunner
{
    private final ConnectionConfig connectionConfig;
    private final ConfigurationLoader configurationLoader;

    @Autowired
    public LiquibaseRunner( final ConfigurationLoader configurationLoader, final ConnectionConfig connectionConfig )
    {
        this.connectionConfig = connectionConfig;
        this.configurationLoader = configurationLoader;
    }

    public Liquibase createLiquibase()
    {
        return createLiquibase( Collections.emptyList() );
    }

    public Liquibase createLiquibase( final List<ChangeSet> changeSets )
    {
        final String connection = getConnection();
        final String changelog = getChangelog();

        final DatabaseChangeLog databaseChangeLog = new DatabaseChangeLog();

        databaseChangeLog.getChangeSets().addAll( changeSets );

        final Config connectionConfig = this.connectionConfig.getConfigValues( connection );
        final Map<String, String> properties = this.connectionConfig.getProperties( connection );
        Properties info = new Properties();
        info.put( "user", connectionConfig.getUsername() );
        info.put( "password", connectionConfig.getPass() );

        try
        {
            Class.forName( connectionConfig.getDriver() ).newInstance();
        }
        catch ( InstantiationException | IllegalAccessException | ClassNotFoundException e )
        {
            e.printStackTrace();
        }

        try
        {
            Connection con = DriverManager.getConnection( connectionConfig.getUrl(), info );
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation( new JdbcConnection( con ) );

            final String source = configurationLoader.getSource();
            final Liquibase liquibase;

            if ( !changeSets.isEmpty() )
            {
                liquibase = new Liquibase( databaseChangeLog, new FileSystemResourceAccessor( source ), database );
            }
            else
            {
                liquibase = new Liquibase( changelog, new FileSystemResourceAccessor( source ), database );
            }

            for ( Map.Entry<String, String> property : properties.entrySet() )
            {
                liquibase.setChangeLogParameter( property.getKey(), property.getValue() );
            }

            return liquibase;
        }
        catch ( SQLException | LiquibaseException e )
        {
            e.printStackTrace();
        }

        return null;
    }
}
