/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.model;

import java.util.Map;

public class Connection
{
    private String name;
    private Config config;
    private Map<String, String> properties;

    public Connection( final Map<Object, Object> connectionObject )
    {
        this.name = ( String ) connectionObject.get( "name" );
        this.properties = ( Map<String, String> ) connectionObject.get( "properties" );
        this.config = new Config( ( Map<Object, Object> ) connectionObject.get( "config" ) );
    }

    public String getName()
    {
        return name;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    public Config getConfig()
    {
        return config;
    }

    public void setConfig( final Config config )
    {
        this.config = config;
    }

    public Map<String, String> getProperties()
    {
        return properties;
    }
}

