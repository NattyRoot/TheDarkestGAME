package tdg.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
    Properties properties;
    static ConfigurationReader configurationReader;

    public static String getString(String key) {
        return getInstance().properties.getProperty(key);
    }

    public static Integer getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    private static ConfigurationReader getInstance() {
        if (configurationReader == null) {
            configurationReader = new ConfigurationReader();
        }
        return configurationReader;
    }

    private ConfigurationReader() {
        properties = new Properties();

        try(InputStream is = getClass().getResourceAsStream("/application.properties")) {
            properties.load(is);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
