package com.roisoftstudio.godutch.info;

import com.google.inject.AbstractModule;
import com.roisoftstudio.godutch.info.services.InfoService;

public class InfoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(InfoService.class);
    }
}