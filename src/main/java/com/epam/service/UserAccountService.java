package com.epam.service;

import com.epam.model.UserAccount;

import java.math.BigDecimal;

public interface UserAccountService {
    UserAccount createUserAccount(UserAccount account);

    UserAccount getUserAccountById(long uAccountId);

    UserAccount getUserAccountByUserId(long uAccountId);

    UserAccount updateUserAccount(UserAccount account);

    boolean deleteUserAccount(long uAccountId);

    void rechargeAccountByAccountId(long accountId, BigDecimal amount);

    void rechargeAccountByUserId(long userId, BigDecimal amount);
}
