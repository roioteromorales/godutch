package com.roisoftstudio.godutch.login.db.dao;

import com.google.inject.Singleton;
import com.roisoftstudio.godutch.login.exceptions.UserAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.User;

@Singleton //TODO SHOULD BE SINGLETON THIS?
public class MongoDbUserDao implements UserDao {
    @Override
    public void addUser(User user) throws UserAlreadyExistsException {
        //// TODO: 09-Mar-16  
    }

    @Override
    public boolean contains(User user) {
        //// TODO: 09-Mar-16
        return false;
    }

    @Override
    public User findByEmail(String userEmail) {
        return null;
    }
}
