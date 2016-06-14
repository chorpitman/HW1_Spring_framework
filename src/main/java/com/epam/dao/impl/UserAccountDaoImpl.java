package com.epam.dao.impl;

import com.epam.dao.UserAccountDao;
import com.epam.model.UserAccount;
import com.epam.model.impl.UserAccountImpl;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserAccountDaoImpl implements UserAccountDao {
    private static Logger log = Logger.getLogger(EventDaoImpl.class.getName());

    private static final String CREATE_USER_ACCOUNT = "INSERT INTO UserAccount (userId, amount) " +
            "VALUES (:userId, :amount)";
    private static final String GET_USER_ACCOUNT = "SELECT * FROM UserAccount WHERE id = :id";
    private static final String UPDATE_USER_ACCOUNT = "UPDATE UserAccount SET userId = :userId, amount = :amount WHERE id = :id";
    private static final String DELETE_USER_ACCOUNT = "DELETE FROM UserAccount WHERE id =:id";
    private static final String GET_USER_ACCOUNT_BY_ID = "SELECT * FROM UserAccount WHERE userId = :userId";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserAccount createUserAccount(UserAccount account) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("userId", account.getUserId());
        namedParameters.put("amount", account.getAmount());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(CREATE_USER_ACCOUNT, new MapSqlParameterSource(namedParameters), keyHolder);
        int userAccountId = keyHolder.getKey().intValue();
        return jdbcTemplate.queryForObject(GET_USER_ACCOUNT, Collections.singletonMap("id", userAccountId), new UserAccountMapper());
    }

    @Override
    public UserAccount getUserAccountById(long uAccountId) {
        return jdbcTemplate.queryForObject(GET_USER_ACCOUNT, Collections.singletonMap("id", uAccountId), new UserAccountMapper());
    }

    @Override
    public UserAccount getUserAccountByUserId(long uAccountId) {
        return jdbcTemplate.queryForObject(GET_USER_ACCOUNT_BY_ID, Collections.singletonMap("userId", uAccountId), new UserAccountMapper());
    }

    @Override
    public UserAccount updateUserAccount(UserAccount account) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("id", account.getId());
        namedParameters.put("userId", account.getUserId());
        namedParameters.put("amount", account.getAmount());
        jdbcTemplate.update(UPDATE_USER_ACCOUNT, namedParameters);
        return account;
    }

    @Override
    public boolean deleteUserAccount(long uAccountId) {
        if (jdbcTemplate.update(DELETE_USER_ACCOUNT, Collections.singletonMap("id", uAccountId)) > 0) {
            return true;
        }
        return false;
    }

    private static final class UserAccountMapper implements RowMapper<UserAccount> {

        @Override
        public UserAccount mapRow(ResultSet resultSet, int i) throws SQLException {
            UserAccount userAccount = new UserAccountImpl();
            userAccount.setId(resultSet.getLong("id"));
            userAccount.setUserId(resultSet.getLong("userId"));
            userAccount.setAmount(resultSet.getBigDecimal("amount"));
            return userAccount;
        }
    }
}
