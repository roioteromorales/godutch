package com.roisoftstudio.godutch.login.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.roisoftstudio.godutch.login.TokenManager;
import com.roisoftstudio.godutch.login.db.dao.TokenDao;
import com.roisoftstudio.godutch.login.db.dao.AccountDao;
import com.roisoftstudio.godutch.login.db.dao.TokenAlreadyExistsException;
import com.roisoftstudio.godutch.login.db.dao.AccountAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class DefaultSignService implements SignService {
    private final Logger logger = LoggerFactory.getLogger(DefaultSignService.class);

    private AccountDao accountDao;
    private final TokenManager tokenManager;
    private final TokenDao tokenDao;

    @Inject
    public DefaultSignService(AccountDao accountDao, TokenManager tokenManager, TokenDao tokenDao) {
        this.accountDao = accountDao;
        this.tokenManager = tokenManager;
        this.tokenDao = tokenDao;
    }

    @Override
    public void signUp(String email, String password) throws SignServiceException {
        Account account = new Account(email, password);
        try {
            accountDao.addAccount(account);
        } catch (AccountAlreadyExistsException e) {
            String msg = "An error occurred when adding the account " + account.getEmail();
            logger.error(msg);
            throw new SignServiceException(msg, e);
        }
    }

    @Override
    public String signIn(String email, String password) throws SignServiceException, InvalidCredentialsException {
        Account account = new Account(email, password);
        validateAccount(account);
        return createToken(account);
    }

    @Override
    public boolean signOut(String token) throws SignServiceException {
        if (tokenDao.hasToken(token)) {
            tokenDao.removeTokenFor(tokenManager.decodeToken(token));
            logger.info("Account with token [" + token + "] has signed out.");
            return true;
        } else {
            logger.error("Cannot sign out Account with token [" + token + "]. It is not signed in.");
            return false;
        }
    }

    @Override
    public boolean isSignedIn(String token) {
        return tokenDao.hasToken(token);
    }

    private String createToken(Account account) throws SignServiceException {
        String token = tokenManager.createToken(account);
        if (tokenDao.hasToken(token)) {
            return token;
        }
        try {
            tokenDao.addToken(account.getEmail(), token);
            logger.info("Account: " + account.getEmail() + " logged in with token: " + token);
            return token;
        } catch (TokenAlreadyExistsException e) {
            String msg = "An error occurred trying to save the token for the account " + account.getEmail();
            logger.error(msg);
            throw new SignServiceException(msg, e);
        }
    }

    private void validateAccount(Account account) throws InvalidCredentialsException {
        if (!accountDao.contains(account)) {
            String msg = "Invalid Credentials";
            logger.error(msg);
            throw new InvalidCredentialsException(msg);
        }
    }
}
