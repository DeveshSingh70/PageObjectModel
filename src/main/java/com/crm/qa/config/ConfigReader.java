package com.crm.qa.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader
 * -------------
 * Loads configuration from config.properties.
 * Fails fast if file is missing (good practice).
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try {
            InputStream inputStream =
                    Thread.currentThread()
                            .getContextClassLoader()
                            .getResourceAsStream("api-config.properties");

            if (inputStream == null) {
                throw new RuntimeException(
                        "config.properties not found in classpath. " +
                                "Ensure it is under src/main/resources");
            }

            properties.load(inputStream);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /**
     * Returns property value for given key
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
