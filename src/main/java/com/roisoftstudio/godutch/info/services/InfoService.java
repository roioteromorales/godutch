package com.roisoftstudio.godutch.info.services;

import com.google.gson.Gson;
import com.roisoftstudio.godutch.config.PropertiesReader;
import com.roisoftstudio.godutch.info.model.InfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static com.roisoftstudio.godutch.config.ConfigurationConstants.CONFIG_FILE_NAME;

public class InfoService {
    final Logger logger = LoggerFactory.getLogger(InfoService.class);

    private final InfoModel infoModel;
    private final PropertiesReader propertiesReader;

    public InfoService() {
        propertiesReader = new PropertiesReader(CONFIG_FILE_NAME);
        infoModel = new InfoModel(propertiesReader.getValue("msName"), getVersion());
    }

    public String getInfo() {
        return new Gson().toJson(infoModel);
    }

    private String getVersion() {
        String msMajorVersion = propertiesReader.getValue("msMajorVersion");
        String msMinorVersion = propertiesReader.getValue("msMinorVersion");
        String version = msMajorVersion + "." + msMinorVersion + "." + getTime();
        logger.info("Getting version: " + version);
        return version;
    }

    private String getTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.getDayOfYear() + "." + now.getHour() + "." + now.getMinute();
    }
}
