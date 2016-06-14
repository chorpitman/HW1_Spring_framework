package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:drop.sql", "classpath:ddl_InMem.sql", "classpath:dml_InMem.sql"})
public class UserServiceTest {
    private User user;

    @Autowired
    private UserService userService;

    @Before
    public void init() {
        user = new UserImpl("Dima", "Dima@i.ua");
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
        User createdUser = userService.createUser(user);
        assertNotSame(null, createdUser.getId());

        assertEquals(createdUser.getName(), user.getName());
        assertEquals(createdUser.getEmail(), user.getEmail());

        User receivedUser = userService.getUserById(createdUser.getId());

        assertEquals(user.getName(), receivedUser.getName());
        assertEquals(user.getEmail(), receivedUser.getEmail());
        assertEquals(createdUser, receivedUser);
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
        User receivedUser = userService.createUser(user);
        userService.deleteUser(receivedUser.getId());
        assertEquals(false, userService.deleteUser(receivedUser.getId()));
    }

    @Test
    public void testDeleteUserWithWrongId() {
        assertEquals(false, userService.deleteUser(0));
        assertEquals(false, userService.deleteUser(-1));
        assertEquals(false, userService.deleteUser(100));
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteUserException() {
        User receivedUser = userService.createUser(user);
        userService.deleteUser(receivedUser.getId());
        assertEquals(receivedUser, userService.getUserById(receivedUser.getId()));
    }

    @Test
    public void testGetUsersByName() {
        final String userName = "John";
        List<User> receivedUser = userService.getUsersByName(userName, 5, 1);

        assertNotNull(receivedUser);
        assertEquals(userName, receivedUser.get(1).getName());
        assertEquals(receivedUser.size(), 5);

        assertEquals(receivedUser.get(0), userService.getUserById(3));
        assertEquals(receivedUser.get(2), userService.getUserById(5));
        assertEquals(receivedUser.get(4), userService.getUserById(7));

        receivedUser = userService.getUsersByName(userName, 2, 2);
        assertEquals(receivedUser.size(), 2);

        receivedUser = userService.getUsersByName(userName, 2, 3);
        assertEquals(receivedUser.size(), 1);
        System.out.println(receivedUser);
        assertEquals(receivedUser, Arrays.asList(userService.getUserById(7)));
    }

    @Test
    public void testGetUserByNameWithWrongParameters() {
        userService.createUser(user);
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), 0, 0));
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), 1, 0));
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), 0, 1));
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), -1, 0));
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), -1, 1));
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), 1, -1));
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), 0, -1));
    }
}