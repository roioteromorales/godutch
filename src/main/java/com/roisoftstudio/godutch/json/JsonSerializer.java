package com.roisoftstudio.godutch.json;

public interface JsonSerializer {

    String toJson(Object object);
    <T> T fromJson(String string, Class<T> clazz);
}
