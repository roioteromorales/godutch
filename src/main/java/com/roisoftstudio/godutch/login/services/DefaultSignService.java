package com.roisoftstudio.godutch.login.services;

import com.google.inject.Inject;
import com.roisoftstudio.godutch.login.TokenManager;
import com.roisoftstudio.godutch.login.db.dao.InMemoryTokenDao;
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
    public DefaultSignService(UserDao userDao) {
        this.userDao = userDao;
        tokenManager = new TokenManager();
        tokenDao = new InMemoryTokenDao();
    }

    @Override
    public String signUp(String email, String password) throws SignServiceException {
        User user = new User(email, password);
        try {
            userDao.addUser(user);
        } catch (UserAlreadyExistsException e) {
            throw new SignServiceException("An error occurred when adding user. ", e);
        }
        String token = tokenManager.createToken(user);
        tokenDao.addToken(token);

        logger.info("added token: " + token);
        return token;
    }

    @Override
    public boolean isSignedIn(String token) {
//        String userEmail = tokenManager.decodeToken(token);
//        User user = userDao.findByEmail(userEmail);
        logger.info("checking token: " + token);
        return tokenDao.hasToken(token);
    }
}
