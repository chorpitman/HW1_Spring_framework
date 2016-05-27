package com.epam.dao.impl;

import com.epam.dao.UserDao;
import com.epam.model.User;
import com.epam.storage.EntityStorage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private static Logger log = Logger.getLogger(UserDaoImpl.class.getName());
    private EntityStorage storage;


    @Autowired
    public void setStorage(EntityStorage storage) {
        log.info("@Autowired storage into UserDaoImpl " + storage);
        this.storage = storage;
    }

    @Override
    public User createUser(User user) {
        log.debug("createUser-" + user);
        return storage.createUser(user);
    }

    @Override
    public User update(User user) {
        log.debug("updateUser-" + user);
        return storage.update(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        log.debug("deleteUser-" + userId);
        return storage.deleteUser(userId);
    }

    @Override
    public User getUserById(long userId) {
        log.debug("getUserById-" + userId);
        return storage.getUserById(userId);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        log.debug("getUsersByName:" + name + " " + "pageSize:" + pageSize + " pageNum:" + pageNum);
        return storage.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User getUserByEmail(String email) {
        log.debug("getUserByEmail:" + email);
        return storage.getUserByEmail(email);
    }
}
