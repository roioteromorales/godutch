package com.roisoftstudio.godutch.login.db;

import com.roisoftstudio.godutch.login.db.dao.InMemoryAccountDao;
import com.roisoftstudio.godutch.login.db.dao.AccountAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.Account;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AccountDaoTest {

    private InMemoryAccountDao accountDao;

    @Before
    public void setUp() throws Exception {
        accountDao = new InMemoryAccountDao();
    }

    @Test
    public void accountAdd_shouldAddAccount() throws Exception {
        Account account = new Account("email", "pass");
        accountDao.addAccount(account);
        assertThat(accountDao.contains(account), is(true));
    }

    @Test(expected = AccountAlreadyExistsException.class)
    public void accountAdd_shouldFailAddingExistingAccount() throws Exception {
        Account account = new Account("email", "pass");
        accountDao.addAccount(account);
        accountDao.addAccount(account);
    }
}