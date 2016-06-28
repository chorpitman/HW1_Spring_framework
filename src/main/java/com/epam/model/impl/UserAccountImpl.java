package com.epam.model.impl;

import com.epam.model.UserAccount;

import java.math.BigDecimal;

public class UserAccountImpl implements UserAccount {
    private static long counterId = 0;

    private long id;
    private long userId;
    private BigDecimal amount;

    public UserAccountImpl() {
    }

    public UserAccountImpl(long accountId,long userId, BigDecimal amount) {
        this.id = accountId;
        this.userId = userId;
        this.amount = amount;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccountImpl that = (UserAccountImpl) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "UserAccountImpl{" +
                "id=" + id +
                ", userId=" + userId +
                ", amount=" + amount +
                '}';
    }
}
