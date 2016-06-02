package com.epam.dao.impl;

import com.epam.dao.UserDao;
import com.epam.model.User;
import com.epam.storage.EntityStorage;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private static Logger log = Logger.getLogger(UserDaoImpl.class.getName());

    public static final String CREATE_USER = "INSERT INTO user (name, email) VALUE(:name, :email)";
    public static final String DELETE_USER = "DELETE FROM user WHERE id = :id";
    public static final String UPDATE_USER = "UPDATE USER SET NAME = ?, EMAIL = ? WHERE id = ?";
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
    public static final String GET_USER_BY_NAME = "SELECT * FROM user WHERE name = ?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";

    private EntityStorage storage;
    private NamedParameterJdbcTemplate jdbcTemplate;


    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        log.debug("createUser-" + user);
        Map nameParameters = new HashMap<>();
        nameParameters.put("name", user.getName());
        nameParameters.put("email", user.getEmail());
        return jdbcTemplate.queryForObject(CREATE_USER, nameParameters, User.class);
    }

    @Override
    public User update(User user) {
        log.debug("updateUser-" + user);
        return storage.update(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        log.debug("deleteUser - " + userId);
        return jdbcTemplate.update(DELETE_USER, userId);
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
