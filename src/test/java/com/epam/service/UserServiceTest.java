package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.dao.UserDao;
import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import com.epam.storage.EntityStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
public class UserServiceTest {
    private User user;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Before
    public void init() {
        user = new UserImpl();
        user.setName("Joe");
        user.setEmail("Joe@i.ua");
    }

    @Test
    public void testCreateUser() {
        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertNotSame(0, createdUser.getId());
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    public void testUpdateUser() {
        final String name = "Jack";
        final String email = "Jack@i.ua";
        user.setName(name);
        user.setEmail(email);

        User updatedUser = userService.updateUser(user);
        assertEquals(name, updatedUser.getName());
        assertEquals(email, updatedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser(user.getId());
        assertEquals(null, user.getEmail());
    }

    @Test
    public void testGetUsersByName() {

    }

}
