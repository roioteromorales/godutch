package com.roisoftstudio.godutch.info.services;

import com.google.gson.Gson;
import com.roisoftstudio.godutch.config.PropertiesReader;
import com.roisoftstudio.godutch.info.model.InfoModel;

import java.time.LocalDateTime;

import static com.roisoftstudio.godutch.config.ConfigurationConstants.CONFIG_FILE_NAME;

public class InfoService {

    private final InfoModel infoModel;
    private final PropertiesReader propertiesReader;

    public InfoService() {
        //future read from file
        propertiesReader = new PropertiesReader(CONFIG_FILE_NAME);
        infoModel = new InfoModel(propertiesReader.getValue("msName"), getVersion());
    }

    public String getInfo() {
        return new Gson().toJson(infoModel);
    }

    private String getVersion() {
        String msMajorVersion = propertiesReader.getValue("msMajorVersion");
        String msMinorVersion = propertiesReader.getValue("msMinorVersion");
        return msMajorVersion + "." + msMinorVersion + "." + getTime();
    }

    private String getTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.getDayOfYear() + "." + now.getHour() + "." + now.getMinute();
    }
}
