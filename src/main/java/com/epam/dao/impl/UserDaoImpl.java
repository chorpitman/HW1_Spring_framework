package com.epam.dao.impl;

import com.epam.dao.UserDao;
import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import com.epam.utils.UserException;
import com.epam.utils.Validator;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private static Logger log = Logger.getLogger(UserDaoImpl.class.getName());

    private static final String CREATE_USER = "INSERT INTO user (name, email) VALUES (:name, :email)";
    private static final String DELETE_USER = "DELETE FROM user WHERE id =:id";
    private static final String UPDATE_USER = "UPDATE user SET name = :name, email = :email WHERE id = :id";
    private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = :id";
    private static final String GET_USER_BY_NAME = "SELECT * FROM user WHERE name = :name LIMIT :start OFFSET :finish";
    private static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = :email";

    private NamedParameterJdbcTemplate jdbcTemplate;


    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        log.debug("createUser-" + user);
        Map<String, Object> nameParameters = new HashMap<>();
        nameParameters.put("name", user.getName());
        nameParameters.put("email", user.getEmail());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(CREATE_USER, new MapSqlParameterSource(nameParameters), keyHolder);
        } catch (DataAccessException e) {
            throw new UserException("You try create not unique user with [name]: " + user.getName() + " [email]: " + user.getEmail());
        }
        int lastUserId = keyHolder.getKey().intValue();
        User createdUser = jdbcTemplate.queryForObject(GET_USER_BY_ID, Collections.singletonMap("id", lastUserId), new UserMapper());
        Validator.checkNotNull(createdUser);
        return createdUser;
    }

    @Override
    public User update(User user) {
        log.debug("updateUser-" + user + "with id " + user.getId());
        // check user in DB
        getUserById(user.getId());
        Map<String, Object> nameParameters = new HashMap<>();
        nameParameters.put("id", user.getId());
        nameParameters.put("name", user.getName());
        nameParameters.put("email", user.getEmail());
        return user;
    }

    @Override
    public boolean deleteUser(long userId) {
        log.debug("deleteUser - " + userId);
        // check user in DB
        getUserById(userId);
        return jdbcTemplate.update(DELETE_USER, Collections.singletonMap("id", userId)) > 0;
    }

    @Override
    public User getUserById(long userId) {
        log.debug("getUserById-" + userId);
        User receivedUser;
        try {
            receivedUser = jdbcTemplate.queryForObject(GET_USER_BY_ID, Collections.singletonMap("id", userId), new UserMapper());
        } catch (DataAccessException e) {
            throw new UserException("user with [id]:" + userId + " doesn't exist");
        }
        Validator.checkNotNull(receivedUser);
        return receivedUser;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        log.debug("getUsersByName:" + name + " " + "pageSize:" + pageSize + " pageNum:" + pageNum);
        int finish = (pageNum - 1) * pageSize;
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("name", name);
        namedParameters.put("start", pageSize);
        namedParameters.put("finish", finish);
        List<User> receivedUser = jdbcTemplate.query(GET_USER_BY_NAME, new MapSqlParameterSource(namedParameters), new UserMapper());
        if (receivedUser.isEmpty()) {
            throw new UserException("users with [name]:" + name + " doesn't exist");
        }
        Validator.checkNotNull(receivedUser);
        return receivedUser;
    }

    @Override
    public User getUserByEmail(String email) {
        log.debug("getUserByEmail:" + email);
        User receivedUser;
        try {
            receivedUser = jdbcTemplate.queryForObject(GET_USER_BY_EMAIL, Collections.singletonMap("email", email), new UserMapper());
        } catch (DataAccessException e) {
            throw new UserException("user with [email]:" + email + " doesn't exist");
        }
        Validator.checkNotNull(receivedUser);
        return receivedUser;
    }

    private static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            log.debug("mapRow:" + resultSet + " " + i);
            User user = new UserImpl();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            return user;
        }
    }
}