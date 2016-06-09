package com.epam.service.impl;

import com.epam.dao.UserAccountDao;
import com.epam.service.UserAccountService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

public class UserAccountServiceImpl implements UserAccountService {
    private static Logger log = Logger.getLogger(UserAccountServiceImpl.class.getName());

    private UserAccountDao userAccountDao;

    public void setAccountDao (UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }
    @Override
    public void rechargeAccount(long userId, BigDecimal amount) {

    }
}
