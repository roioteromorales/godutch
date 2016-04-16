package com.roisoftstudio.godutch;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.roisoftstudio.godutch.db.DbModule;
import com.roisoftstudio.godutch.info.InfoModule;
import com.roisoftstudio.godutch.login.LoginModule;
import com.squarespace.jersey2.guice.JerseyGuiceModule;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

import java.util.ArrayList;
import java.util.List;

public class AppContextListener extends GuiceServletContextListener {
    public static Injector injector;

    @Override
    protected Injector getInjector() {

        List<Module> modules = new ArrayList<>();

        modules.add(new JerseyGuiceModule("__HK2_Generated_0"));
        modules.add(new ServletModule());
        modules.add(new DbModule());
        modules.add(new LoginModule());
        modules.add(new InfoModule());

        injector = Guice.createInjector(modules);

        JerseyGuiceUtils.install(injector); //todo this shouldnt be here, dunno where. Should run after injector is  created.
        return injector;
    }
}

