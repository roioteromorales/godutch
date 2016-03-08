package com.roisoftstudio.godutch.login.services;

import com.roisoftstudio.godutch.login.TokenManager;
import com.roisoftstudio.godutch.login.model.User;
import com.roisoftstudio.godutch.login.exceptions.UserAlreadyExistsException;
import com.roisoftstudio.godutch.login.db.dao.InMemoryUserDao;

public class SignService {

    private InMemoryUserDao userDao;
    private final TokenManager tokenManager;

    public SignService(InMemoryUserDao userDao) {
        this.userDao = userDao;
        tokenManager = new TokenManager();
    }

    public String signUp(String email, String password) throws UserAlreadyExistsException {
        User user = new User(email, password);
        userDao.addUser(user);
        return tokenManager.createToken(user);
    }

    public boolean isSignedIn(String token) {
        return userDao.contains(tokenManager.decodeToken(token));
    }
}
