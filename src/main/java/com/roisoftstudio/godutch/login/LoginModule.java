package com.roisoftstudio.godutch.login;

import com.google.inject.AbstractModule;
import com.roisoftstudio.godutch.login.db.dao.InMemoryUserDao;
import com.roisoftstudio.godutch.login.db.dao.UserDao;
import com.roisoftstudio.godutch.login.services.DefaultSignService;
import com.roisoftstudio.godutch.login.services.SignService;

public class LoginModule extends AbstractModule {//ServletModule {

    @Override
    protected void configure() {
        bind(UserDao.class).to(InMemoryUserDao.class);
        bind(SignService.class).to(DefaultSignService.class);
    }

}
