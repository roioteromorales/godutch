package com.roisoftstudio.godutch.info.model;

import com.roisoftstudio.godutch.Model;

public class InfoModel implements Model {

    private String msName;
    private String msVersion;

    public InfoModel(final String msName, final String msVersion) {
        this.msName = msName;
        this.msVersion = msVersion;
    }

    public String getMsName() {
        return msName;
    }

    public void setMsName(final String msName) {
        this.msName = msName;
    }

    public String getMsVersion() {
        return msVersion;
    }

    public void setMsVersion(final String msVersion) {
        this.msVersion = msVersion;
    }
}
