package com.epam.dao;

import com.epam.config.ServiceTestConfig;
import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class UserDaoTest {
    private User user;

    @Autowired
    private UserDao userDao;

    @Before
    public void init() {
        user = new UserImpl(1L, "Bob", "bob@i.ua");
    }

    @Test
    public void testCreateUser() {
        User createdUser = userDao.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user.getName(), createdUser.getName());
        assertEquals(user.getEmail(), createdUser.getEmail());
    }

    @Test
    public void testUpdate() {
        final String name = "Sam";
        final String email = "Sam@i.ua";

        User createdUser = userDao.createUser(user);
        createdUser.setName(name);
        createdUser.setEmail(email);

        User updatedUser = userDao.update(createdUser);
        assertEquals(name, updatedUser.getName());
        assertEquals(email, updatedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        User createdUser = userDao.createUser(user);
        assertEquals(true, userDao.deleteUser(createdUser.getId()));
    }

    @Test
    public void testDeleteUserWithWrongId() {
        assertEquals(false, userDao.deleteUser(0));
        assertEquals(false, userDao.deleteUser(-1));
        assertEquals(false, userDao.deleteUser(100));
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteUserException() {
        User createdUser = userDao.createUser(user);
        userDao.deleteUser(createdUser.getId());
        assertEquals(createdUser, userDao.getUserById(createdUser.getId()));
        assertEquals(false, userDao.getUserById(createdUser.getId()));
    }

    @Test
    public void testGetUserById() {
        long userId = 1L;
        assertNotNull(userDao.getUserById(userId));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testGetUserByIdWrongParam() {
        long userId = 0L;
        assertNotNull(userDao.getUserById(userId));

        userId = -1L;
        assertNotNull(userDao.getUserById(userId));

        userId = 100L;
        assertNotNull(userDao.getUserById(userId));
    }

    @Test
    public void testGetUsersByName() {
        final String userName = "John";
        List<User> receivedUser = userDao.getUsersByName(userName, 5, 1);

        assertEquals(userName, receivedUser.get(1).getName());
        assertEquals(receivedUser.size(), 5);
        assertNotNull(receivedUser);

        assertEquals(receivedUser.get(0), userDao.getUserById(3));
        assertEquals(receivedUser.get(2), userDao.getUserById(5));
        assertEquals(receivedUser.get(4), userDao.getUserById(7));

        receivedUser = userDao.getUsersByName(userName, 2, 2);
        assertEquals(receivedUser.size(), 2);

        receivedUser = userDao.getUsersByName(userName, 2, 3);
        assertEquals(receivedUser.size(), 1);
        System.out.println(receivedUser);
        assertEquals(receivedUser, Arrays.asList(userDao.getUserById(7)));
    }

    @Test
    public void testGetUserByEmail() {
        final String userEmail = user.getEmail();
        assertNotNull(userEmail);

        User createdUser = userDao.createUser(user);
        assertEquals(userEmail, createdUser.getEmail());
        assertNotSame(null, createdUser.getEmail());
    }
}