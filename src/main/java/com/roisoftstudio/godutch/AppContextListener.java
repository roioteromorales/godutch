package com.roisoftstudio.godutch;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.roisoftstudio.godutch.login.db.dao.InMemoryUserDao;
import com.roisoftstudio.godutch.login.db.dao.UserDao;
import com.roisoftstudio.godutch.login.services.DefaultSignService;
import com.roisoftstudio.godutch.login.services.SignService;

public class AppContextListener extends GuiceServletContextListener {

    public static Injector injector;

    @Override
    protected Injector getInjector() {
        injector = Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                bind(UserDao.class).to(InMemoryUserDao.class);
                bind(SignService.class).to(DefaultSignService.class);
            }
        });

        return injector;
    }
}

