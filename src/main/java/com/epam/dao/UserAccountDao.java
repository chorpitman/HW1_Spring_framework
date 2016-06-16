package com.epam.dao;

import com.epam.model.UserAccount;

public interface UserAccountDao {

    UserAccount createUserAccount(UserAccount account);

    UserAccount getUserAccountById(long uAccountId);

    UserAccount getUserAccountByUserId(long uAccountId);

    UserAccount updateUserAccount(UserAccount account);

    boolean deleteUserAccount(long uAccountId);


// TODO: 07.06.2016 ask about 2methods
//    void rechargeAccountByUserId(long userId, BigDecimal amount);
//    BigDecimal getAmount();
//    void setAmount(BigDecimal amount);
}
