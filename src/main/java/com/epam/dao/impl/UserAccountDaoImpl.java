package com.epam.dao.impl;

import com.epam.dao.UserAccountDao;
import com.epam.model.UserAccount;
import com.epam.model.impl.UserAccountImpl;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserAccountDaoImpl implements UserAccountDao {
    private static Logger log = Logger.getLogger(EventDaoImpl.class.getName());

    private static final String CREATE_USER_ACCOUNT = "INSERT INTO UserAccount (userId, amount) " +
            "value (:userId, :amount)";
    private static final String GET_USER_ACCOUNT = "";
    private static final String UPDATE_USER_ACCOUNT = "";
    private static final String DELETE_USER_ACCOUNT = "";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserAccount createUserAccount(UserAccount account) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("userId", account.getUserId());
        namedParameters.put("amount", account.getAmount());
        jdbcTemplate.update(CREATE_USER_ACCOUNT, namedParameters);
        return account;
    }

    @Override
    public UserAccount getUserAccountbyId(long uAccountId) {
        return jdbcTemplate.queryForObject(GET_USER_ACCOUNT, Collections.singletonMap("id", uAccountId), new UserAccountMapper());
    }

    @Override
    public UserAccount updateUserAccount(UserAccount account) {

        return null;
    }

    @Override
    public boolean deleteUserAccount(long uAccountId) {
        jdbcTemplate.update(DELETE_USER_ACCOUNT, Collections.singletonMap("id", uAccountId));
        return true;
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
