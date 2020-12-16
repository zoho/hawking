//$Id$
package com.zoho.hawking.datetimeparser.constants;

import com.zoho.hawking.datetimeparser.configuration.Configuration;

public class ConfigurationConstants {
    public static Configuration configuration;

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static void setConfiguration(Configuration config) {
        configuration = config;
    }
}
