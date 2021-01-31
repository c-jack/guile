/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.constants;

/**
 * Constants class for defining the URI address values observed in page requests.
 * <p>
 * Keeping them here helps prevent any typos/editing in the controllers, whilst also allowing easy reference to
 * existing values.
 */
public final class PageUriConstants
{
    // General
    public static final String BASE = "/";
    public static final String BLANK = "/blank";
    public static final String SPINNER = "/spinner";
    public static final String CONNECTION = "/set_connection";
    public static final String CHANGELOG = "/set_changelog";

    // Error
    public static final String ERROR = "/error";
    public static final String ERROR_403 = "/403";
    public static final String SET_ERROR = "/error/set";

    // Info
    public static final String INFOBOX = "/infoBox";

    /*
     * Shared
     */
    public static final String INFOBOX_SHOW = "/infobox/show";

    /*
     * Settings
     */
    public static final String SETTINGS_INDEX = "settings";
    public static final String SETTINGS_SET_WS = "settings/set";

    /**
     * Private Constructor to prevent class from being instantiated
     */
    private PageUriConstants()
    {
        throw new UnsupportedOperationException();
    }
}
