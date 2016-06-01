package com.epam.dao;

import java.math.BigDecimal;

/**
 * Created by Oleg_Chorpita on 6/1/2016.
 */
public interface UserAccountDao {
    void rechargeAccount(long userId, BigDecimal amount);
}
