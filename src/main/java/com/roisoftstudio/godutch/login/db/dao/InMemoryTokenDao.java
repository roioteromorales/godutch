package com.roisoftstudio.godutch.login.db.dao;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class InMemoryTokenDao implements TokenDao {

    private final List<String> tokenList;

    public InMemoryTokenDao() {
        tokenList = new ArrayList<>();
    }

    @Override
    public void addToken(String token) {
        tokenList.add(token);
    }

    @Override
    public boolean hasToken(String token) {
        return tokenList.contains(token);
    }
}
