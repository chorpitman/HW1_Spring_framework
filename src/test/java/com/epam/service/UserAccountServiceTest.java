package com.epam.service;

import com.epam.dao.UserAccountDao;
import com.epam.model.UserAccount;
import com.epam.model.impl.UserAccountImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Configuration
@ImportResource("classpath:app-context.xml")
@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {

    @InjectMocks
    private UserAccountImpl mockUserAccount;

    private UserAccount account;

    @Mock
    private UserAccountDao mockUserAccountDao;

    @Autowired
    private UserAccountService userAccountService;

    @Before
    public void init() {
        mockUserAccount = new UserAccountImpl(1L, 1L, BigDecimal.TEN);
    }

    @Test
    public void testCreateUserAccount() {
        when(mockUserAccountDao.createUserAccount(mockUserAccount)).thenReturn(mockUserAccount);
        UserAccount createdUserAccount = mockUserAccountDao.createUserAccount(mockUserAccount);
        verify(mockUserAccountDao).createUserAccount(mockUserAccount);

        assertNotNull(createdUserAccount);
        assertEquals(mockUserAccount.getUserId(), createdUserAccount.getUserId());
        assertTrue(mockUserAccount.getAmount().compareTo(createdUserAccount.getAmount()) == 0);
    }

    @Test
    public void testGetUserAccountById() {
        when(mockUserAccountDao.createUserAccount(mockUserAccount)).thenReturn(mockUserAccount);
        UserAccount createdUserAccount = mockUserAccountDao.createUserAccount(mockUserAccount);
        verify(mockUserAccountDao).createUserAccount(mockUserAccount);

        when(mockUserAccountDao.getUserAccountById(createdUserAccount.getId())).thenReturn(createdUserAccount);
        assertEquals(createdUserAccount, mockUserAccountDao.getUserAccountById(createdUserAccount.getId()));
        verify(mockUserAccountDao).getUserAccountById(createdUserAccount.getId());
        assertNotNull(mockUserAccountDao.getUserAccountById(createdUserAccount.getId()));
    }

    @Test
    public void testGetUserAccountByUserId() {
        when(mockUserAccountDao.createUserAccount(mockUserAccount)).thenReturn(mockUserAccount);
        UserAccount createdUserAccount = mockUserAccountDao.createUserAccount(mockUserAccount);
        verify(mockUserAccountDao).createUserAccount(mockUserAccount);

        when(mockUserAccountDao.getUserAccountByUserId(createdUserAccount.getUserId())).thenReturn(createdUserAccount);
        assertEquals(createdUserAccount, mockUserAccountDao.getUserAccountByUserId(createdUserAccount.getUserId()));
        verify(mockUserAccountDao).getUserAccountByUserId(createdUserAccount.getUserId());
        assertNotNull(mockUserAccountDao.getUserAccountByUserId(createdUserAccount.getUserId()));
    }

    @Test
    public void testUpdateUserAccount() {
        when(mockUserAccountDao.createUserAccount(mockUserAccount)).thenReturn(mockUserAccount);
        UserAccount createdUserAccount = mockUserAccountDao.createUserAccount(mockUserAccount);
        verify(mockUserAccountDao).createUserAccount(mockUserAccount);

        long userAccountId = 2L;
        BigDecimal balance = BigDecimal.ONE;

        createdUserAccount.setUserId(userAccountId);
        createdUserAccount.setAmount(balance);

        when(mockUserAccountDao.updateUserAccount(createdUserAccount)).thenReturn(createdUserAccount);
        UserAccount updatedUserAccount = mockUserAccountDao.updateUserAccount(createdUserAccount);
        assertEquals(balance, updatedUserAccount.getAmount());
        assertEquals(userAccountId, updatedUserAccount.getUserId());
    }

    @Test
    public void testDeleteUserAccount() {
        long userAccountId = 2L;
        when(mockUserAccountDao.deleteUserAccount(userAccountId)).thenReturn(Boolean.TRUE);
        assertEquals(true, mockUserAccountDao.deleteUserAccount(userAccountId));
        assertEquals(false, mockUserAccountDao.deleteUserAccount(0));
        assertEquals(false, mockUserAccountDao.deleteUserAccount(-1));
        assertEquals(false, mockUserAccountDao.deleteUserAccount(100));
    }
}
