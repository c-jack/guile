/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.constants;

/**
 * Constants class for defining various constants used across the application
 */
public final class ApplicationConstants
{
    // General
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String COLON = ":";
    public static final String EMPTY_STRING = "";
    public static final String ESCAPED_DBL_QUOTE = "\"";
    public static final String SINGLE_SPACE = " ";
    public static final String NEW_LINE = "\n";

    // Date
    public static final String DD_MM_YYYY = "dd/MM/yyyy";

    // Messages and Logging
    public static final String STRING_PARAM_MARKER = "%s";
    public static final String NO_FILES_FOR_PROCESSING = "No {} files for processing in {}";

    // Path and File Constants
    public static final String TITLE_ATTRIBUTE = "title";
    public static final String CONNECTION_LIST = "connectionList";
    public static final String DIRECTORY = "directory";
    public static final String CONNECTION = "connection";
    public static final String CHANGELOG = "changelog";
    public static final String STATUS = "status";
    public static final String RESULT = "result";
    public static final String MESSAGE = "message";

    /**
     * Private Constructor to prevent class from being instantiated
     */
    private ApplicationConstants()
    {
        throw new UnsupportedOperationException();
    }
}
