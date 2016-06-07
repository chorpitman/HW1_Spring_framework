package com.epam.model;

import java.math.BigDecimal;

public interface UserAccount {
    long getId();

    void setId(long id);

    long getUserId();

    void setUserId(long id);

    BigDecimal getAmount();

    void setAmount(BigDecimal amount);
}