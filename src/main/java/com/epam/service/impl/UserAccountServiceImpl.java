package com.epam.service.impl;

import com.epam.dao.UserAccountDao;
import com.epam.model.UserAccount;
import com.epam.service.UserAccountService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

public class UserAccountServiceImpl implements UserAccountService {
    private static Logger log = Logger.getLogger(UserAccountServiceImpl.class.getName());

    private UserAccountDao userAccountDao;

    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Override
    public void rechargeAccount(long userId, BigDecimal amount) {
        UserAccount userAccount = userAccountDao.getUserAccountByUserId(userId);

        BigDecimal startBalance = new BigDecimal(0);
        BigDecimal userBalance = userAccount.getAmount();

        if (userBalance.compareTo(startBalance) == 1) {
            userAccount.setAmount(userBalance.add(amount));
        } else {
            userAccount.setAmount(amount);
        }
        userAccountDao.updateUserAccount(userAccount);
    }
}
