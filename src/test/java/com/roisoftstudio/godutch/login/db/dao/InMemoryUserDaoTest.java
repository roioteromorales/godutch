package com.roisoftstudio.godutch.login.db.dao;

import com.roisoftstudio.godutch.login.exceptions.UserAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.User;
import org.junit.Test;

public class InMemoryUserDaoTest {
    @Test (expected = UserAlreadyExistsException.class)
    public void addTwiceUsers_shouldThrowException() throws Exception {
        InMemoryUserDao inMemoryUserDao = new InMemoryUserDao();
        User user = new User("1", "1");
        inMemoryUserDao.addUser(user);
        inMemoryUserDao.addUser(user);

    }
}