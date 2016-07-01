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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class UserAccountServiceImplTest {

    private UserAccount userAccount;

    @Autowired
    private UserAccountService userAccountService;

    @Test
    public void testCreateUserAccount() {
        userAccount = new UserAccountImpl();
        userAccount.setUserId(1);
        userAccount.setAmount(BigDecimal.TEN);

        UserAccount createdUserAccount = userAccountService.createUserAccount(userAccount);
        assertNotNull(createdUserAccount);
        assertEquals(userAccount.getUserId(), createdUserAccount.getUserId());
        assertTrue(userAccount.getAmount().compareTo(createdUserAccount.getAmount()) == 0);
    }

    @Test
    public void testGetUserAccountById() {
        long userId = 2L;
        assertNotNull(userAccountService.getUserAccountById(userId));
    }

    @Test
    public void testGetUserAccountByUserId() {
        long userAccountId = 2L;
        assertNotNull(userAccountService.getUserAccountById(userAccountId));
    }

    @Test
    public void testUpdateUserAccount() {
        long userAccountId = 2L;
        BigDecimal balance = BigDecimal.TEN;

        UserAccount receivedUserAccount = userAccountService.getUserAccountById(userAccountId);
        assertNotNull(receivedUserAccount);

        receivedUserAccount.setAmount(balance);

        userAccountService.updateUserAccount(receivedUserAccount);
        assertTrue(userAccountService.getUserAccountById(userAccountId).getAmount().compareTo(balance) == 0);
    }

    @Test
    public void testDeleteUserAccount() {
        long userAccountId = 2L;
        assertEquals(true, userAccountService.deleteUserAccount(userAccountId));
        assertEquals(false, userAccountService.deleteUserAccount(0));
        assertEquals(false, userAccountService.deleteUserAccount(-1));
        assertEquals(false, userAccountService.deleteUserAccount(100));
    }

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
