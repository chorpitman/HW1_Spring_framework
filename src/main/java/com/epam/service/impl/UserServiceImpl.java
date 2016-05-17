package com.epam.service.impl;

import com.epam.dao.UserDao;
import com.epam.model.User;
import com.epam.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    //impl methods
    public User getUserById(long userId) {
        return userDao.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
    //Todo pagination impl
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return null;
    }

    public User createUser(User user) {
        return userDao.createUser(user);
    }

    public User updateUser(User user) {
        return userDao.update(user);
    }

    public boolean deleteUser(long userId) {
        return userDao.deleteUser(userId);
    }
}
