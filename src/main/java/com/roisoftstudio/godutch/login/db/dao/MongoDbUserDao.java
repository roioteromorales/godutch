package com.roisoftstudio.godutch.login.db.dao;

import com.google.inject.Singleton;
import com.roisoftstudio.godutch.login.model.User;

@Singleton //TODO SHOULD BE SINGLETON THIS?
public class MongoDbUserDao implements UserDao {
    @Override
    public void addUser(User user) throws UserAlreadyExistsException {
    }

    @Override
    public boolean contains(User user) {
        return false;
    }

    @Override
    public User findByEmail(String userEmail) {
        return null;
    }
}
