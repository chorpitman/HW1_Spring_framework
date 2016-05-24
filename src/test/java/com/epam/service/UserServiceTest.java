package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
public class UserServiceTest {
    private User user;

    @Autowired
    private UserService userService;

    @Before
    public void init() {
        user = new UserImpl("Joe", "Joe@i.ua");
    }

    @After
    public void cleanStorage() {
        userService.deleteUser(user.getId());
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
    public void testGetUserById() {
        long userId = user.getId();
        assertNotNull(userId);

        User createdUser = userService.createUser(user);
        assertNotSame(null, createdUser.getId());
        assertEquals(userId, createdUser.getId());
    }

    @Test
    public void testGetUserByEmail() {
        String userEmail = user.getEmail();
        assertNotNull(userEmail);

        User createdUser = userService.createUser(user);
        assertNotSame(null, createdUser.getEmail());
        assertEquals(userEmail, createdUser.getEmail());

    }

    @Test
    public void testUpdateUser() {
        final String name = "Jack";
        final String email = "Jack@i.ua";
        userService.createUser(user);
        user.setName(name);
        user.setEmail(email);

        User updatedUser = userService.updateUser(user);
        assertEquals(name, updatedUser.getName());
        assertEquals(email, updatedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        long idUser = user.getId();
        userService.createUser(user);
        userService.deleteUser(user.getId());
        assertEquals(null, userService.getUserById(idUser));
    }

    @Test
    public void testGetUsersByName() {
        userService.createUser(user);
        //test pagination
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), 0, 1));
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), 0, 0));
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), 1, 0));

        //check result of logic
        assertEquals(Arrays.asList(user), userService.getUsersByName(user.getName(), 1, 1));

        //test logic
        List<User> resultUserList = userService.getUsersByName(user.getName(), 1, 1);
        assertEquals(1, resultUserList.size());

        //check name
        User user = resultUserList.get(0);
        assertEquals(user.getName(), user.getName());
    }
}
