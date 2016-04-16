package com.roisoftstudio.godutch.config;

public class ConfigurationConstants {
    public static String CONFIG_FILE_NAME = "config.properties";

    static {
        System.out.println("STATIC");

        if (System.getenv("GODUTCH_DEV") != null) {
            CONFIG_FILE_NAME = "config.dev.properties";
            System.out.println("STATIC - VAR " + CONFIG_FILE_NAME);
        }
    }
    public static PropertiesReader propertiesReader = new PropertiesReader(CONFIG_FILE_NAME);

    public static final String PROTOCOL = propertiesReader.getValue("webProtocol");
    public static final String DOCKER_IP = propertiesReader.getValue("ip");
    public static final String PORT = propertiesReader.getValue("webPort");

    public static final String CONTAINER_URL = PROTOCOL + "://" + DOCKER_IP + ":" + PORT + "/";

}
