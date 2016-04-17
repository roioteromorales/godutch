package com.roisoftstudio.godutch.login.db.dao;

import com.roisoftstudio.godutch.login.model.Account;

public interface AccountDao {
    void addAccount(Account account) throws AccountAlreadyExistsException;
    boolean contains(Account account);
}
