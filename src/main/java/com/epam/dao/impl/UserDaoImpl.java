package com.epam.dao.impl;

import com.epam.dao.UserDao;
import com.epam.model.User;
import com.epam.storage.EntityStorage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private EntityStorage storage;

    @Autowired
    public void setStorage(EntityStorage storage) {
        this.storage = storage;
    }

    @Override
    public User createUser(User user) {
        return storage.createUser(user);
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
    public User getUserById(long userId) {
        return storage.getUserById(userId);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return storage.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User getUserByEmail(String email) {
        return storage.getUserByEmail(email);
    }
}
