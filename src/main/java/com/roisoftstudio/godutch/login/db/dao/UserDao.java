package com.roisoftstudio.godutch.login.db.dao;

import com.roisoftstudio.godutch.login.exceptions.UserAlreadyExistsException;
import com.roisoftstudio.godutch.login.model.User;

public interface UserDao {
    void addUser(User user) throws UserAlreadyExistsException;
    boolean contains(User user);
}
