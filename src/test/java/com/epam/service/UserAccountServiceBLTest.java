package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.model.UserAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class UserAccountServiceBLTest {

    private UserAccount userAccount;

    @Autowired
    private UserAccountService userAccountService;

    @Test
    public void testRechargeAccountByUserIdWithZeroStartBalance() {
        long userId = 2L;
        BigDecimal rechargeBalance = BigDecimal.TEN;

        userAccount = userAccountService.getUserAccountByUserId(userId);
        assertTrue(userAccount.getAmount().compareTo(BigDecimal.ZERO) == 0);

        userAccountService.rechargeAccountByUserId(userId, rechargeBalance);
        userAccount = userAccountService.getUserAccountByUserId(userId);
        assertTrue(userAccount.getAmount().compareTo(rechargeBalance) == 0);
    }

    @Test
    public void testRechargeAccountByUserIdWithSomeStartBalance() {
        long userId = 1L;
        BigDecimal rechargeBalance = BigDecimal.TEN;

        userAccount = userAccountService.getUserAccountByUserId(userId);
        assertTrue(userAccount.getAmount().compareTo(BigDecimal.ZERO) == 1);

        userAccountService.rechargeAccountByUserId(userId, rechargeBalance);
        userAccount = userAccountService.getUserAccountByUserId(userId);
        assertTrue(userAccount.getAmount().compareTo(rechargeBalance) == 1);
    }

    @Test
    public void testRechargeAccountByAccountIdWithZeroStartBalance() {
        long accountId = 2L;
        BigDecimal rechargeBalance = BigDecimal.TEN;

        userAccount = userAccountService.getUserAccountByUserId(accountId);
        assertTrue(userAccount.getAmount().compareTo(BigDecimal.ZERO) == 0);

        userAccountService.rechargeAccountByAccountId(accountId, rechargeBalance);
        userAccount = userAccountService.getUserAccountByUserId(accountId);
        assertTrue(userAccount.getAmount().compareTo(rechargeBalance) == 0);
    }

    @Test
    public void testRechargeAccountByAccountId() {
        long accountId = 1L;
        BigDecimal rechargeBalance = BigDecimal.ONE;

        userAccount = userAccountService.getUserAccountByUserId(accountId);
        assertTrue(userAccount.getAmount().compareTo(BigDecimal.ZERO) == 1);

        userAccountService.rechargeAccountByAccountId(accountId, rechargeBalance);
        userAccount = userAccountService.getUserAccountByUserId(accountId);
        assertTrue(userAccount.getAmount().compareTo(rechargeBalance) == 1);
    }
}
