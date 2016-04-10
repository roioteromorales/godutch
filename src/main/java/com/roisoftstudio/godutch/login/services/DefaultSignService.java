package com.roisoftstudio.godutch.login.services;

import com.google.inject.Inject;
import com.roisoftstudio.godutch.login.TokenManager;
import com.roisoftstudio.godutch.login.db.dao.TokenDao;
import com.roisoftstudio.godutch.login.db.dao.UserDao;
import com.roisoftstudio.godutch.login.exceptions.SignServiceException;
import com.roisoftstudio.godutch.login.exceptions.UserAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultSignService implements SignService {
    final Logger logger = LoggerFactory.getLogger(DefaultSignService.class);

    private UserDao userDao;
    private final TokenManager tokenManager;
    private final TokenDao tokenDao;

    @Inject
    public DefaultSignService(UserDao userDao, TokenManager tokenManager, TokenDao tokenDao) {
        this.userDao = userDao;
        this.tokenManager = tokenManager;
        this.tokenDao = tokenDao;
    }

    @Override
    public String signUp(String email, String password) throws SignServiceException {
        User user = new User(email, password);
        try {
            userDao.addUser(user);
        } catch (UserAlreadyExistsException e) {
            throw new SignServiceException("An error occurred when adding the user " + user.getEmail(), e);
        }
        String token = tokenManager.createToken(user);
        tokenDao.addToken(token);

        logger.info("User: " + user.getEmail() + " logged in with token: " + token);
        return token;
    }

    @Override
    public boolean signIn(String email, String password) throws SignServiceException {
        User user = new User(email, password);
        String token = tokenManager.createToken(user);
        logger.info("User: " + user.getEmail() + " logged in with token: " + token);
        return tokenDao.hasToken(token);
    }

    @Override
    public boolean isSignedIn(String token) {
        return tokenDao.hasToken(token);
    }
}
