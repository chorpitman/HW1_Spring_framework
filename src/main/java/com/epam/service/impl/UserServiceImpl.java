package com.epam.service.impl;

import com.epam.dao.UserAccountDao;
import com.epam.dao.UserDao;
import com.epam.model.User;
import com.epam.model.impl.UserAccountImpl;
import com.epam.service.UserService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static Logger log = Logger.getLogger(UserServiceImpl.class.getName());

    private UserDao userDao;
    private UserAccountDao userAccountDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    //impl methods
    public User getUserById(long userId) {
        return userDao.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        log.debug("get users by name " + name);
        return userDao.getUsersByName(name, pageSize, pageNum);
    }

    public User createUser(User user) {
        User createdUser = userDao.createUser(user);
        long idCreatedUser = createdUser.getId();
        userAccountDao.createUserAccount(new UserAccountImpl(idCreatedUser, new BigDecimal(0)));
        return createdUser;
    }

    public User updateUser(User user) {
        return userDao.update(user);
    }

    public boolean deleteUser(long userId) {
        return userDao.deleteUser(userId);
    }
}
