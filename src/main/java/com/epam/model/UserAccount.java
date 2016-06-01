package com.epam.model;

import java.math.BigDecimal;

/**
 * Created by Oleg_Chorpita on 5/30/2016.
 */
public interface UserAccount {
    long getId();

    void setId(long id);

    long getUserId();

    void setUserId(long id);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);
}
