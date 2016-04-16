package com.roisoftstudio.godutch.login.db.dao;

import com.google.inject.Singleton;
import com.roisoftstudio.godutch.login.model.Account;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class InMemoryAccountDao implements AccountDao {
    private final Map<String, String> accountList;

    public InMemoryAccountDao() {
        accountList = new HashMap<>();
    }

    @Override
    public void addAccount(Account account) throws AccountAlreadyExistsException {
        if (accountList.containsKey(account.getEmail()))
            throw new AccountAlreadyExistsException("Cannot add another account in the list with the same key: " + account.getEmail());
        else
            accountList.put(account.getEmail(), account.getPassword());
    }
    @Override
    public boolean contains(Account account) {
        return accountList.containsKey(account.getEmail());
    }

}
