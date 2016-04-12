package com.roisoftstudio.godutch.login.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.roisoftstudio.godutch.login.TokenManager;
import com.roisoftstudio.godutch.login.db.dao.TokenDao;
import com.roisoftstudio.godutch.login.db.dao.UserDao;
import com.roisoftstudio.godutch.login.db.dao.TokenAlreadyExistsException;
import com.roisoftstudio.godutch.login.db.dao.UserAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class DefaultSignService implements SignService {
    private final Logger logger = LoggerFactory.getLogger(DefaultSignService.class);

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
    public void signUp(String email, String password) throws SignServiceException {
        User user = new User(email, password);
        try {
            userDao.addUser(user);
        } catch (UserAlreadyExistsException e) {
            String msg = "An error occurred when adding the user " + user.getEmail();
            logger.error(msg);
            throw new SignServiceException(msg, e);
        }
    }

    @Override
    public String signIn(String email, String password) throws SignServiceException, InvalidCredentialsException {
        User user = new User(email, password);
        validateUser(user);
        return createToken(user);
    }

    private String createToken(User user) throws SignServiceException {
        String token = tokenManager.createToken(user);
        if (tokenDao.hasToken(token)) {
            return token;
        }
        try {
            tokenDao.addToken(user.getEmail(), token);
            logger.info("User: " + user.getEmail() + " logged in with token: " + token);
            return token;
        } catch (TokenAlreadyExistsException e) {
            String msg = "An error occurred trying to save the token for the user " + user.getEmail();
            logger.error(msg);
            throw new SignServiceException(msg, e);
        }
    }

    private void validateUser(User user) throws InvalidCredentialsException {
        if (!userDao.contains(user)) {
            String msg = "Invalid Credentials";
            logger.error(msg);
            throw new InvalidCredentialsException(msg);
        }
    }

    @Override
    public boolean signOut(String token) throws SignServiceException {
        if (tokenDao.hasToken(token)) {
            tokenDao.removeTokenFor(tokenManager.decodeToken(token));
            logger.info("User with token [" + token + "] has signed out.");
            return true;
        } else {
            logger.error("Cannot sign out User with token [" + token + "]. It is not signed in.");
            return false;
        }
    }

    @Override
    public boolean isSignedIn(String token) {
        return tokenDao.hasToken(token);
    }
}
