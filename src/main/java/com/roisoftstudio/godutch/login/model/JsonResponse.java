package com.roisoftstudio.godutch.login.model;

//this class can be a model for any response we could add a map of values or just some fields
// i dont know but would be nice to create a standard response
public class JsonResponse {
    private String data;

    public JsonResponse(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
