package com.roisoftstudio.godutch;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.roisoftstudio.godutch.login.db.dao.InMemoryUserDao;
import com.roisoftstudio.godutch.login.db.dao.UserDao;
import com.roisoftstudio.godutch.login.services.DefaultSignService;
import com.roisoftstudio.godutch.login.services.SignService;
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
        modules.add(new ServletModule() {
            @Override
            protected void configureServlets() {
                bind(UserDao.class).to(InMemoryUserDao.class);
                bind(SignService.class).to(DefaultSignService.class);
            }
        });
        modules.add(new AbstractModule() {
            @Override
            protected void configure() {
                // ...
            }
        });

        injector = Guice.createInjector(modules);

        JerseyGuiceUtils.install(injector); //todo this shouldnt be here, dunno where. Should run after injector is created.
        return injector;
    }


}

