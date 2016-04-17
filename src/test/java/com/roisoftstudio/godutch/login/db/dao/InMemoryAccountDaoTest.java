package com.roisoftstudio.godutch.login.db.dao;

import com.roisoftstudio.godutch.login.model.Account;
import org.junit.Test;

public class InMemoryAccountDaoTest {
    @Test (expected = AccountAlreadyExistsException.class)
    public void addTwiceAccount_shouldThrowException() throws Exception {
        InMemoryAccountDao inMemoryAccountDao = new InMemoryAccountDao();
        Account account = new Account("1", "1");
        inMemoryAccountDao.addAccount(account);
        inMemoryAccountDao.addAccount(account);

    }
}