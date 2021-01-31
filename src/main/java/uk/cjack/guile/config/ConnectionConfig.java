/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import uk.cjack.guile.model.Config;
import uk.cjack.guile.model.Connection;

@Component
public class ConnectionConfig
{
    private Map<String, Connection> connectionList;

    @PostConstruct
    public void getConnections()
    {
        connectionList = new HashMap<>();

        try
        {
            //the base folder is ./, the root of the main.properties file
            String path = "./config/connections.yml";

            //load the file handle for main.properties
            final FileInputStream fileInputStream = new FileInputStream( path );

            Yaml yaml = new Yaml();
            final Map<Object, Object> connectionObjects = yaml.load( fileInputStream );

            for ( Map.Entry<Object, Object> connection : connectionObjects.entrySet() )
            {
                connectionList.put( ( String ) connection.getKey(),
                        new Connection( ( Map<Object, Object> ) connection.getValue() ) );
            }
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
    }

    public Connection getConnection( final String connectionName )
    {
        return connectionList.get( connectionName );
    }

    public Config getConfigValues( final String connectionName )
    {
        if ( connectionList != null )
        {
            return connectionList.get( connectionName ).getConfig();
        }
        return null;
    }

    public Map<String, String> getProperties( final String connectionName )
    {
        return connectionList.get( connectionName ).getProperties();
    }

    public Set<String> getConnectionList()
    {
        return connectionList.keySet();
    }
}
