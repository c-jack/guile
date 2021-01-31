/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */

package uk.cjack.guile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nz.net.ultraq.thymeleaf.LayoutDialect;

/**
 * Servlet Configuration Class
 * <p>
 * Contains HTTP and Thymeleaf related method overrides.
 */
@Configuration
public class ServletConfig
{

    /**
     * Enables the UltraQ Thymeleaf Layout Dialect
     *
     * @return {@link LayoutDialect} object for Thymeleaf
     */
    @Bean
    public LayoutDialect layoutDialect()
    {
        return new LayoutDialect();
    }


}