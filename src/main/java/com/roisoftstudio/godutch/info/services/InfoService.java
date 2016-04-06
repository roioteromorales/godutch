package com.roisoftstudio.godutch.info.services;

import com.google.gson.Gson;
import com.roisoftstudio.godutch.info.model.InfoModel;

public class InfoService {

    private final InfoModel infoModel;

    public InfoService() {
        //future read from file
        String msName = "GoDutch MicroService";
        String msVersion = "0.1";
        infoModel = new InfoModel(msName, msVersion);
    }

    public String getInfo() {
        return new Gson().toJson(infoModel);
    }
}
