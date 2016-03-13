package com.roisoftstudio.godutch.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    private Properties properties = new Properties();

    public PropertiesReader(String propertiesFile) {
        initPropertiesFromFile(propertiesFile);
    }

    private void initPropertiesFromFile(String propertiesFile) {
        String filePath = getClass().getClassLoader().getResource(propertiesFile).getFile();
        File file = new File(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String propertyName) {
        return properties.getProperty(propertyName);
    }
}