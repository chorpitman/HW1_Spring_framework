package com.epam.service;

import com.epam.dao.UserDao;
import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Configuration
@ImportResource("classpath:app-context.xml")
@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserImpl user;

    @Mock
    private UserDao mockUserDao;

    @Before
    public void init() {
        user = new UserImpl(1L, "Bob", "bob@i.ua");
    }

    @Test
    public void testCreateUser() {
        when(mockUserDao.createUser(user)).thenReturn(user);
        User createdUser = mockUserDao.createUser(user);
        verify(mockUserDao).createUser(user);
        assertNotNull(createdUser);

        assertNotSame(0, createdUser.getId());
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    public void testGetUserById() {
        when(mockUserDao.createUser(user)).thenReturn(user);
        User createdUser = mockUserDao.createUser(user);
        verify(mockUserDao).createUser(user);
        assertNotSame(null, createdUser.getId());

        assertEquals(createdUser.getName(), user.getName());
        assertEquals(createdUser.getEmail(), user.getEmail());

        when(mockUserDao.getUserById(user.getId())).thenReturn(user);
        User receivedUser = mockUserDao.getUserById(createdUser.getId());
        verify(mockUserDao).getUserById(user.getId());

        assertEquals(user.getName(), receivedUser.getName());
        assertEquals(user.getEmail(), receivedUser.getEmail());
        assertEquals(createdUser, receivedUser);
    }

    @Test
    public void testGetUserByEmail() {
        when(mockUserDao.createUser(user)).thenReturn(user);
        User createdUser = mockUserDao.createUser(user);
        verify(mockUserDao).createUser(user);
        assertNotSame(null, createdUser.getEmail());

        when(mockUserDao.getUserByEmail(user.getEmail())).thenReturn(user);
        assertNotNull(user.getEmail());
        assertEquals(mockUserDao.getUserByEmail(user.getEmail()).getEmail(), createdUser.getEmail());
        verify(mockUserDao).getUserByEmail(user.getEmail());
    }

    @Test
    public void testUpdateUser() {
        final String name = "Jack";
        final String email = "Jack@i.ua";

        when(mockUserDao.createUser(user)).thenReturn(user);
        User createdUser = mockUserDao.createUser(user);
        verify(mockUserDao).createUser(user);
        assertNotSame(null, createdUser);

        user.setEmail(email);
        user.setName(name);

        when(mockUserDao.update(user)).thenReturn(user);
        User updatedUser = mockUserDao.update(user);
        verify(mockUserDao).update(user);

        assertEquals(name, updatedUser.getName());
        assertEquals(email, updatedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        long userId = 2L;
        when(mockUserDao.deleteUser(userId)).thenReturn(Boolean.TRUE);
        assertEquals(true, mockUserDao.deleteUser(userId));
        assertEquals(false, mockUserDao.deleteUser(0));
        assertEquals(false, mockUserDao.deleteUser(-1));
        assertEquals(false, mockUserDao.deleteUser(100));
        verify(mockUserDao).deleteUser(userId);
    }

    @Test
    public void testDeleteUserWithWrongId() {
        assertEquals(false, mockUserDao.deleteUser(0));
        verify(mockUserDao).deleteUser(0);

        assertEquals(false, mockUserDao.deleteUser(-1));
        verify(mockUserDao).deleteUser(-1);

        assertEquals(false, mockUserDao.deleteUser(100));
        verify(mockUserDao).deleteUser(100);
    }

    @Test
    public void testGetUsersByName() {
        when(mockUserDao.getUsersByName(user.getName(), 1, 1)).thenReturn(Arrays.asList(user));
        List<User> receivedUser = mockUserDao.getUsersByName(user.getName(), 1, 1);
        verify(mockUserDao).getUsersByName(user.getName(), 1, 1);

        assertNotNull(receivedUser);
        assertEquals(user.getName(), receivedUser.get(0).getName());
        assertTrue(receivedUser.containsAll(Arrays.asList(user)));
    }
}