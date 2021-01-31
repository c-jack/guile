/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.constants;

/**
 * Constants class for defining the Thymeleaf template values returned to the View.
 * <p>
 * These values refer to the templates stored in the Resources/Templates directory.
 * Keeping them here helps prevent any typos/editing in the methods, whilst also allowing easy reference to
 * existing values.
 */
public final class PageTemplateConstants
{
    // General
    public static final String BLANK_TEMPLATE = "blank";
    public static final String ERROR_TEMPLATE = "page/error";
    public static final String ERROR_403_TEMPLATE = "page/403";
    public static final String HOME_TEMPLATE = "page/home";


    public static final String INFO_FRAGMENT = "fragments/helpers :: info";
    public static final String SPINNER_FRAGMENT = "fragments/helpers :: spinner";
    public static final String PAGE_403 = "page/403";
    public static final String RESULT = "page/result";


    /**
     * Private Constructor to prevent class from being instantiated
     */
    private PageTemplateConstants()
    {
        throw new UnsupportedOperationException();
    }
}
