package com.epam.dao.impl;

import com.epam.dao.UserDao;
import com.epam.model.User;
import com.epam.storage.EntityStorage;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private EntityStorage storage;

    @Override
    public User createUser(User user) {
        return storage.addUser(user);
    }

    @Override
    public User getById(long userId) {
        return storage.getUser(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return storage.getUserByEmail(email);
    }

    @Override
    public User update(User user) {
        return storage.update(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        return storage.deleteUser(userId);
    }
    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return storage.getUsersByName(name, pageSize, pageNum);
    }
}
