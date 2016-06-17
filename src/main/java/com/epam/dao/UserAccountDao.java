package com.epam.dao;

import com.epam.model.UserAccount;

public interface UserAccountDao {

    UserAccount createUserAccount(UserAccount account);

    UserAccount getUserAccountById(long uAccountId);

    UserAccount getUserAccountByUserId(long uAccountId);

    UserAccount updateUserAccount(UserAccount account);

    boolean deleteUserAccount(long uAccountId);
}
