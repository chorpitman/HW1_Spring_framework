package com.epam.dao.impl;

import com.epam.dao.UserDao;
import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private static Logger log = Logger.getLogger(UserDaoImpl.class.getName());

    private static final String CREATE_USER = "INSERT INTO user (name, email) VALUE(:name, :email)";
    private static final String DELETE_USER = "DELETE FROM user WHERE id =:id";
    private static final String UPDATE_USER = "UPDATE user SET name = :name, email = :email WHERE id = :id";
    private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE id = :id";
    private static final String GET_USER_BY_NAME = "SELECT * FROM user WHERE name = :name";
    private static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = :email";

    private NamedParameterJdbcTemplate jdbcTemplate;


    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User createUser(User user) {
        log.debug("createUser-" + user);
        Map<String, String> nameParameters = new HashMap<>();
        nameParameters.put("name", user.getName());
        nameParameters.put("email", user.getEmail());
        jdbcTemplate.update(CREATE_USER, nameParameters); // TODO: 05.06.2016 ask about queryForObject. Do not work when compile;
        return user;
    }

    @Override
    public User update(User user) {
        log.debug("updateUser-" + user + "with id " + user.getId());
        Map<String, Object> nameParameters = new HashMap<>();
        nameParameters.put("id", user.getId());
        nameParameters.put("name", user.getName());
        nameParameters.put("email", user.getEmail());
        jdbcTemplate.update(UPDATE_USER, nameParameters);
        return user;
    }

    @Override
    public boolean deleteUser(long userId) {
        log.debug("deleteUser - " + userId);
        jdbcTemplate.update(DELETE_USER, Collections.singletonMap("id", userId));
        return true;
    }

    @Override
    public User getUserById(long userId) {
        log.debug("getUserById-" + userId);
        return jdbcTemplate.queryForObject(GET_USER_BY_ID, Collections.singletonMap("id", userId), new UserMapper());
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        log.debug("getUsersByName:" + name + " " + "pageSize:" + pageSize + " pageNum:" + pageNum);
        int start = pageSize;
        int finish = (pageNum - 1) * pageSize;
        String sql = GET_USER_BY_NAME + " LIMIT " + start + " OFFSET " + finish;
        return jdbcTemplate.query(sql, Collections.singletonMap("name", name), new UserMapper());
    }

    @Override
    public User getUserByEmail(String email) {
        log.debug("getUserByEmail:" + email);
        return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL, Collections.singletonMap("email", email), new UserMapper());
    }

    public static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new UserImpl();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            return user;
        }
    }
}
