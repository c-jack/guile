/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.model;

import java.util.Map;

public class Config
{
    private final String username;
    private final String pass;
    private final String url;
    private final String driver;

    public Config( final Map<Object, Object> connectionObject )
    {
        this.username = ( String ) connectionObject.get( "username" );
        this.pass = ( String ) connectionObject.get( "password" );
        this.url = ( String ) connectionObject.get( "url" );
        this.driver = ( String ) connectionObject.get( "driver" );
    }

    public String getUsername()
    {
        return username;
    }

    public String getPass()
    {
        return pass;
    }

    public String getUrl()
    {
        return url;
    }

    public String getDriver()
    {
        return driver;
    }


}
