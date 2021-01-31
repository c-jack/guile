/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import uk.cjack.guile.constants.ApplicationConstants;

/**
 * ConfigUtil
 * <p>
 * This static class is used to set and serve config
 *
 * @author chris.jackson
 */
@ControllerAdvice( annotations = Controller.class )
public class ConfigUtil
{
    private static String connection;
    private static String changelog;

    /**
     * Public Constructor
     */
    @SuppressWarnings( "squid:S1118" )
    public ConfigUtil()
    {
        // Constructor required due to status as ControllerAdvice
    }

    /**
     * @return The connection.
     * <p>
     */
    @ModelAttribute( ApplicationConstants.CONNECTION )
    public static String getConnection()
    {
        return connection;
    }

    /**
     * @return The changelog.
     * <p>
     */
    @ModelAttribute( ApplicationConstants.CHANGELOG )
    public static String getChangelog()
    {
        return changelog;
    }


    public static void setConnection( final String connection )
    {
        ConfigUtil.connection = connection;
    }

    public static void setChangelog( final String changelog )
    {
        ConfigUtil.changelog = changelog;
    }
}
