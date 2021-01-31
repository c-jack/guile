/*
 * Copyright 2022 Chris Jackson [github.com/c-jack]
 */
package uk.cjack.guile.config;

import static uk.cjack.guile.constants.ConfigConstants.*;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Property Loader
 * <p>
 * Loads the property files used to configure the Integration Tests
 *
 * @author chris.jackson
 */
@Configuration
@ConfigurationProperties( prefix = "config" )
public class ConfigurationLoader
{
    @Value( SOURCE )
    private String source;

    public String getSource()
    {
        return source;
    }

    public void setSource( final String source )
    {
        this.source = source;
    }
}
