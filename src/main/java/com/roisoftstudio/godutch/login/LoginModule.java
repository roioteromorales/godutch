package com.roisoftstudio.godutch.login;

import com.google.inject.AbstractModule;
import com.roisoftstudio.godutch.json.GsonSerializer;
import com.roisoftstudio.godutch.json.JsonSerializer;
import com.roisoftstudio.godutch.login.db.dao.*;
import com.roisoftstudio.godutch.login.services.DefaultSignService;
import com.roisoftstudio.godutch.login.services.SignService;

public class LoginModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountDao.class).to(InMemoryAccountDao.class);
        bind(SignService.class).to(DefaultSignService.class);
        bind(TokenDao.class).to(InMemoryTokenDao.class);
        bind(JsonSerializer.class).to(GsonSerializer.class);
        bind(TokenManager.class);

    }

}
