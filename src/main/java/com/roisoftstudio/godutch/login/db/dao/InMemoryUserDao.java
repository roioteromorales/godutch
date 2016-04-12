package com.roisoftstudio.godutch.login.db.dao;

import com.google.inject.Singleton;
import com.roisoftstudio.godutch.login.model.User;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class InMemoryUserDao implements UserDao {
    private final Map<String, String> userList;

    public InMemoryUserDao() {
        userList = new HashMap<>();
    }

    @Override
    public void addUser(User user) throws UserAlreadyExistsException {
        if (userList.containsKey(user.getEmail()))
            throw new UserAlreadyExistsException("Cannot add another user in the list with the same key: " + user.getEmail());
        else
            userList.put(user.getEmail(), user.getPassword());
    }
    @Override
    public boolean contains(User user) {
        return userList.containsKey(user.getEmail());
    }

    @Override
    public User findByEmail(String userEmail) {
        String pass = userList.get(userEmail);
        return new User(userEmail, pass);
    }
}
