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
    public UserAccount createUserAccount(UserAccount account) {
        return userAccountDao.createUserAccount(account);
    }

    @Override
    public UserAccount getUserAccountById(long uAccountId) {
        return userAccountDao.getUserAccountById(uAccountId);
    }

    @Override
    public UserAccount getUserAccountByUserId(long uAccountId) {
        return userAccountDao.getUserAccountByUserId(uAccountId);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount account) {
        return userAccountDao.updateUserAccount(account);
    }

    @Override
    public boolean deleteUserAccount(long uAccountId) {
        return userAccountDao.deleteUserAccount(uAccountId);
    }

    public void rechargeAccountByUserId(long userId, BigDecimal amount) {
        log.debug("rechargeAccountByUserId:  userId " + userId + " amount: " +amount );
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

    public void rechargeAccountByAccountId(long userAccountId, BigDecimal amount) {
        log.debug("rechargeAccountByUserId:  userId " + userAccountId + " amount: " +amount );
        UserAccount userAccount = userAccountDao.getUserAccountById(userAccountId);

        BigDecimal uAccountBalance = userAccount.getAmount();

        if (uAccountBalance.compareTo(BigDecimal.ZERO) == 1) {
            userAccount.setAmount(uAccountBalance.add(amount));
        } else {
            userAccount.setAmount(amount);
        }
        userAccountDao.updateUserAccount(userAccount);
    }
}
