package com.roisoftstudio.godutch.login.db;

import com.roisoftstudio.godutch.login.db.dao.InMemoryUserDao;
import com.roisoftstudio.godutch.login.db.dao.UserAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.User;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserDaoTest {

    private InMemoryUserDao userDao;

    @Before
    public void setUp() throws Exception {
        userDao = new InMemoryUserDao();
    }

    @Test
    public void userAdd_shouldAddUser() throws Exception {
        User user = new User("email", "pass");
        userDao.addUser(user);
        assertThat(userDao.contains(user), is(true));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void userAdd_shouldFailAddingExistingUser() throws Exception {
        User user = new User("email", "pass");
        userDao.addUser(user);
        userDao.addUser(user);
    }
}