package com.epam.service.impl;

import com.epam.dao.UserAccountDao;
import com.epam.service.UserAccountService;

import java.math.BigDecimal;

public class UserAccountServiceImpl implements UserAccountService {
    private UserAccountDao userAccountDao;

    @Override
    public void rechargeAccount(long userId, BigDecimal amount) {
        userAccountDao.rechargeAccount(userId, amount);
    }
}
