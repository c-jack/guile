/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */
package uk.cjack.guile.utils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * MessageUtil
 * <p>
 * This static class is used to set and serve temporary error and information values that are provided back to the UI
 * for display to the user.
 *
 * @author chris.jackson
 */
@ControllerAdvice( annotations = Controller.class )
public final class MessageUtil
{
    private static String[] errorMessage;
    private static String[] infoMessage;

    /**
     * Public Constructor
     */
    @SuppressWarnings( "squid:S1118" )
    public MessageUtil()
    {
        // Constructor required due to status as ControllerAdvice
    }

    /**
     * @return The errorMessage.
     * <p>
     * Message is set to a local var for return, and the error message purged
     * so that it only shows once.
     */
    @ModelAttribute( "errorMessage" )
    public static String[] getErrorMessage()
    {
        final String[] returnedMessage = errorMessage;
        clearErrorMessage();
        return returnedMessage;
    }

    /**
     * Clears the error message
     */
    private static void clearErrorMessage()
    {
        MessageUtil.errorMessage = null;
    }

    /**
     * Set an Error Message.
     *
     * @param errorTitle   The title to set.
     * @param errorMessage The errorMessage to set.
     */
    public static void setErrorMessage( final String errorTitle, final String errorMessage )
    {
        MessageUtil.errorMessage = new String[]{ errorTitle, errorMessage };
    }

    /**
     * @return The infoMessage.
     * <p>
     * Message is set to a local var for return, and the info message purged
     * so that it only shows once.
     */
    @ModelAttribute( "infoMessage" )
    public static String[] getInfoMessage()
    {
        final String[] returnedMessage = infoMessage;
        clearInfoMessage();
        return returnedMessage;
    }

    /**
     * Clears the info message
     */
    private static void clearInfoMessage()
    {
        MessageUtil.infoMessage = null;
    }

    /**
     * Set an Info Message
     *
     * @param infoTitle   The title to set
     * @param infoMessage The message to set.
     */
    public static void setInfoMessage( final String infoTitle, final String infoMessage )
    {
        MessageUtil.infoMessage = new String[]{ infoTitle, infoMessage };
    }
}
