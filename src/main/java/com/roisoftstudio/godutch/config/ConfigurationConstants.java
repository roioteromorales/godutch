package com.roisoftstudio.godutch.config;

public class ConfigurationConstants {
    public static final String CONFIG_FILE_NAME = "config.properties";
    public static PropertiesReader propertiesReader = new PropertiesReader(CONFIG_FILE_NAME);
    public static final String PORT = propertiesReader.getValue("port");

    public static final String DOCKER_HOST = System.getenv("DOCKER_HOST");
    public static final String CONTAINER_URL = DOCKER_HOST
            .replace("tcp", "http")
            .subSequence(0, DOCKER_HOST.length() - 3) + PORT + "/godutch-" +  "0.1" + "/"; // TODO SOMETHING WITH VERSION AND PATH

}
