package com.roisoftstudio.godutch.info.services;

import com.google.gson.Gson;
import com.roisoftstudio.godutch.info.model.InfoModel;

public class InfoService {

    private final InfoModel infoModel;

    public InfoService() {
        //future read from file
        infoModel = new InfoModel("GoDutch Microservice", "0.1");
    }

    public String getInfo() {
        return new Gson().toJson(infoModel);
    }
}
