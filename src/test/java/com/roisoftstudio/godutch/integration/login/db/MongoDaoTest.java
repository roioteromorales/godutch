package com.roisoftstudio.godutch.integration.login.db;

import com.roisoftstudio.godutch.db.DbClient;
import com.roisoftstudio.godutch.login.db.dao.MongoDbAccountDao;
import com.roisoftstudio.godutch.login.db.dao.AccountAlreadyExistsException;
import com.roisoftstudio.godutch.login.db.dao.AccountDao;
import com.roisoftstudio.godutch.login.model.Account;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.roisoftstudio.godutch.integration.login.LoginHelpers.aRandomAccount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MongoDaoTest {


    private AccountDao mongoDbAccountDao;

    @Before
    public void setUp() throws Exception {
        mongoDbAccountDao = new MongoDbAccountDao(new DbClient());
    }

    @Test
    public void notExistingAccount_shouldNotBeInDb() throws Exception {
        assertThat(mongoDbAccountDao.contains(aRandomAccount())).isFalse();
    }

    @Test
    public void addedAccount_shouldBeInDb() throws Exception {
        Account account = aRandomAccount();
        mongoDbAccountDao.addAccount(account);
        assertThat(mongoDbAccountDao.contains(account)).isTrue();
    }

    @Ignore //TODO To fix this we need to create a primary key in the mongo db
    @Test
    public void whenInsertingTwiceTheSameAccount_dbShouldThrowException() throws Exception {
        Account account = aRandomAccount();
        mongoDbAccountDao.addAccount(account);

        assertThatExceptionOfType(AccountAlreadyExistsException.class)
                .isThrownBy(() ->  mongoDbAccountDao.addAccount(account));
    }


}
