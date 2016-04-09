package com.roisoftstudio.godutch.json;

import com.google.gson.Gson;

public class GsonSerializer implements JsonSerializer {

    private final Gson gson;

    public GsonSerializer() {
        gson = new Gson();
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T fromJson(String string, Class<T> clazz) {
        return gson.fromJson(string, clazz);
    }
}
