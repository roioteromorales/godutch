package com.roisoftstudio.godutch.login.services;

import com.google.inject.Inject;
import com.roisoftstudio.godutch.login.TokenManager;
import com.roisoftstudio.godutch.login.db.dao.UserDao;
import com.roisoftstudio.godutch.login.exceptions.UserAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.User;

public class DefaultSignService implements SignService {

    private UserDao userDao;
    private final TokenManager tokenManager;

    @Inject
    public DefaultSignService(UserDao userDao) {
        this.userDao = userDao;
        tokenManager = new TokenManager();
    }

    @Override
    public String signUp(String email, String password) throws UserAlreadyExistsException {
        User user = new User(email, password);
        userDao.addUser(user);
        return tokenManager.createToken(user);
    }

    @Override
    public boolean isSignedIn(String token) {
        return userDao.contains(tokenManager.decodeToken(token));
    }
}
