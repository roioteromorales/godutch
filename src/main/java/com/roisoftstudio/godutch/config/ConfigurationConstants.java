package com.roisoftstudio.godutch.config;

public class ConfigurationConstants {
    public static final String CONFIG_FILE_NAME = "config.properties";
    public static PropertiesReader propertiesReader = new PropertiesReader(CONFIG_FILE_NAME);

    public static final String PROTOCOL = propertiesReader.getValue("webProtocol");
    public static final String DOCKER_IP = propertiesReader.getValue("ip");
    public static final String PORT = propertiesReader.getValue("webPort");

    public static final String CONTAINER_URL = PROTOCOL + "://" + DOCKER_IP + ":" + PORT + "/";

}
