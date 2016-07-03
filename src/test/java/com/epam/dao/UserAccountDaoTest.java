package com.epam.dao;

import com.epam.config.ServiceTestConfig;
import com.epam.model.UserAccount;
import com.epam.model.impl.UserAccountImpl;
import com.epam.utils.UserAccountException;
import org.junit.Before;
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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class UserAccountDaoTest {

    private UserAccount userAccount;

    @Autowired
    private UserAccountDao accountDao;

    @Before
    public void init() {
        userAccount = new UserAccountImpl(1L, 1L, BigDecimal.TEN);
    }

    @Test
    public void testCreateUserAccount() {
        UserAccount createdUserAccount = accountDao.createUserAccount(userAccount);
        assertNotSame(null, createdUserAccount);
        assertEquals(userAccount.getUserId(), createdUserAccount.getUserId());
        assertTrue(userAccount.getAmount().compareTo(createdUserAccount.getAmount()) == 0);
    }

    @Test
    public void testGetUserAccountById() {
        assertNotNull(accountDao.getUserAccountById(1L));
    }

    @Test
    public void testGetUserAccountByUserId() {
        assertNotNull(accountDao.getUserAccountById(1L));
    }

    @Test
    public void testUpdateUserAccount() {
        UserAccount receivedUserAcc = accountDao.getUserAccountById(1L);
        receivedUserAcc.setAmount(BigDecimal.ONE);
        assertEquals(BigDecimal.ONE, accountDao.updateUserAccount(receivedUserAcc).getAmount());
    }

    @Test
    public void testDeleteUserAccount() {
        assertEquals(true, accountDao.deleteUserAccount(1L));
    }

    @Test(expected = UserAccountException.class)
    public void testDeleteUserAccountWithWrongUserAccount() {
        assertEquals(false, accountDao.deleteUserAccount(-1L));
        assertEquals(false, accountDao.deleteUserAccount(0L));
        assertEquals(false, accountDao.deleteUserAccount(100L));
    }

    @Test(expected = UserAccountException.class)
    public void testDeleteUserAccountNotExistUserAccount() {
        assertEquals(true, accountDao.deleteUserAccount(1L));
        assertEquals(false, accountDao.deleteUserAccount(1L));
    }
}