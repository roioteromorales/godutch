package com.roisoftstudio.godutch.login.db.dao;

import com.roisoftstudio.godutch.login.exceptions.UserAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.User;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDao implements UserDao {
    private final Map<String, String> userList;

    public InMemoryUserDao() {
        userList = new HashMap<>();
    }

    public void addUser(User user) throws UserAlreadyExistsException{
        if (userList.containsKey(user.getEmail()))
            throw new UserAlreadyExistsException();
        else
            userList.put(user.getEmail(),user.getPassword());
    }

    public boolean contains(User user) {
        return userList.containsKey(user.getEmail());
    }
}
