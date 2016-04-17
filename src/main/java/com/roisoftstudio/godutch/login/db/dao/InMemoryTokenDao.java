package com.roisoftstudio.godutch.login.db.dao;

import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class InMemoryTokenDao implements TokenDao {

    private final Map<String, String> tokenMap;

    public InMemoryTokenDao() {
        tokenMap = new HashMap<>();
    }

    @Override
    public void addToken(String email, String token) throws TokenAlreadyExistsException {
        if (tokenMap.containsKey(email))
            throw new TokenAlreadyExistsException("The email [" + email + "] already has a token.");
        tokenMap.put(email, token);
    }

    @Override
    public void removeTokenFor(String email) {
        tokenMap.remove(email);
    }

    @Override
    public boolean hasToken(String token) {
        return tokenMap.containsValue(token);
    }
}
