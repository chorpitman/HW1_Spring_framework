package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        long idUser = user.getId();
        userService.createUser(user);
        userService.deleteUser(user.getId());
        assertEquals(true, userService.getUserById(idUser));
    }

    @Test
    public void testGetUsersByName() {
        userService.createUser(user);
        //test pagination
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), 0, 1));
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), 0, 0));
        assertEquals(Collections.emptyList(), userService.getUsersByName(user.getName(), 1, 0));

        //test logic
        List<User> resultUserList = userService.getUsersByName(user.getName(), 1, 1);
        assertEquals(1, resultUserList.size());

        //test retusrned name
        String name = "";
        for (User user1 : resultUserList) {
            if (user1.getName() == user.getName()) {
                name = user1.getName();
            }
        }
        assertNotSame("Jack", name);
        assertEquals("Joe", name);
    }
}
