package com.epam.service;

import com.epam.model.UserAccount;

import java.math.BigDecimal;

/**
 * Created by Oleg_Chorpita on 6/1/2016.
 */
public interface UserAccountService {
    UserAccount createUserAccount(UserAccount account);

    UserAccount getUserAccountById(long uAccountId);

    UserAccount getUserAccountByUserId(long uAccountId);

    UserAccount updateUserAccount(UserAccount account);

    boolean deleteUserAccount(long uAccountId);

    void rechargeAccountByAccountId(long accountId, BigDecimal amount);

    void rechargeAccountByUserId(long userId, BigDecimal amount);
}
