package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
    private static ConfigFileReader configReader = new ConfigFileReader();

    private Properties mProps = new Properties();

    public static ConfigFileReader getInstance() {
        return configReader;
    }

    public void loadConfigFile(String filename) throws IOException {
        FileInputStream inputStream = new FileInputStream(filename);
        mProps.load(inputStream);
    }

    public String getValue(String key) {
        return mProps.getProperty(key);
    }
    public String getValue(String key, String defaultVal) {
        return mProps.getProperty(key, defaultVal);
    }
}
