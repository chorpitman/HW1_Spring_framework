package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.model.UserAccount;
import com.epam.model.impl.UserAccountImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:drop.sql", "classpath:ddl_InMem.sql", "classpath:dml_InMem.sql"})
public class UserAccountServiceImplTest {
    private UserAccount userAccount;

    @Autowired
    UserAccountService userAccountService;

    @Test
    public void testCreateUserAccount() {
        userAccount = new UserAccountImpl();
        userAccount.setUserId(1);
        userAccount.setAmount(new BigDecimal(100000));

        UserAccount createdUserAccount = userAccountService.createUserAccount(userAccount);
        assertNotNull(createdUserAccount);
        assertEquals(userAccount.getUserId(), createdUserAccount.getUserId());
        assertTrue(userAccount.getAmount().compareTo(createdUserAccount.getAmount()) == 0);
    }

    @Test
    public void testGetUserAccountById() {
        long userId = 2;
        assertNotNull(userAccountService.getUserAccountById(userId));

    }

    @Test
    public void testGetUserAccountByUserId() {
        long userAccountId = 2;
        assertNotNull(userAccountService.getUserAccountById(userAccountId));
    }

    @Test
    public void testUpdateUserAccount() {
        long userAccountId = 2;
        BigDecimal setBalance = new BigDecimal(100);

        UserAccount userAccount = userAccountService.getUserAccountById(userAccountId);
        assertNotNull(userAccount);

        BigDecimal userBalance = userAccount.getAmount();
        userAccount.setAmount(setBalance);
        BigDecimal newBalance = userAccount.getAmount();
        assertNotSame(userBalance, newBalance);
    }

    @Test
    public void testDeleteUserAccount() {
        long userAccountId = 2;
        assertEquals(true, userAccountService.deleteUserAccount(userAccountId));
        assertEquals(false, userAccountService.deleteUserAccount(0));
        assertEquals(false, userAccountService.deleteUserAccount(-1));
    }

    @Test
    public void testRechargeAccountByUserId() {
        long userAccountId = 2;
        BigDecimal rechargeBalance = new BigDecimal(100);

        UserAccount userAccount = userAccountService.getUserAccountByUserId(userAccountId);
        assertTrue(userAccount.getAmount().compareTo(new BigDecimal(0)) == 0);

        userAccountService.rechargeAccountByAccountId(userAccountId, rechargeBalance);

        userAccount = userAccountService.getUserAccountByUserId(userAccountId);
        assertTrue(userAccount.getAmount().compareTo(rechargeBalance) == 0);
    }

    @Test
    public void testRechargeAccountByAccountId() {
        long userId = 2;
        BigDecimal rechargeBalance = new BigDecimal(100);

        UserAccount userAccount = userAccountService.getUserAccountByUserId(userId);
        assertTrue(userAccount.getAmount().compareTo(new BigDecimal(0)) == 0);

        userAccountService.rechargeAccountByAccountId(userId, rechargeBalance);

        userAccount = userAccountService.getUserAccountByUserId(userId);
        assertTrue(userAccount.getAmount().compareTo(rechargeBalance) == 0);
    }
}
