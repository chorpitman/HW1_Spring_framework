package com.epam.service;

import java.math.BigDecimal;

/**
 * Created by Oleg_Chorpita on 6/1/2016.
 */
public interface UserAccountService {

    void rechargeAccount(long userId, BigDecimal amount);
}
