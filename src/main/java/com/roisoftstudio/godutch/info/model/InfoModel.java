package com.roisoftstudio.godutch.info.model;

import com.roisoftstudio.godutch.Model;

public class InfoModel implements Model {

    private String msName;
    private String msVersion;

    public InfoModel(String msName, String msVersion) {
        this.msName = msName;
        this.msVersion = msVersion;
    }

    public String getMsName() {
        return msName;
    }

    public void setMsName(String msName) {
        this.msName = msName;
    }

    public String getMsVersion() {
        return msVersion;
    }

    public void setMsVersion(String msVersion) {
        this.msVersion = msVersion;
    }
}
