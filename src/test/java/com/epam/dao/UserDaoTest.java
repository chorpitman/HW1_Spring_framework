package com.epam.dao;

import com.epam.config.ServiceTestConfig;
import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import com.epam.utils.UserException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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

    @Test(expected = UserException.class)
    public void testCreateUserExistUser() {
        User existUser = userDao.getUserById(1L);
        assertNotNull(existUser);
        userDao.createUser(existUser);
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

    @Test(expected = UserException.class)
    public void testUpdateException() {
        user = new UserImpl(100L, "x", "y");
        userDao.update(user);
    }

    @Test
    public void testDeleteUser() {
        User createdUser = userDao.createUser(user);
        assertEquals(true, userDao.deleteUser(createdUser.getId()));
    }

    @Test(expected = UserException.class)
    public void testDeleteUserWithWrongId() {
        assertEquals(false, userDao.deleteUser(0));
        assertEquals(false, userDao.deleteUser(-1));
        assertEquals(false, userDao.deleteUser(100));
    }

    @Test(expected = UserException.class)
    public void testDeleteNotExistUser() {
        User createdUser = userDao.createUser(user);
        assertEquals(createdUser, userDao.getUserById(createdUser.getId()));
        assertEquals(true, userDao.deleteUser(createdUser.getId()));
        userDao.deleteUser(createdUser.getId());
    }

    @Test
    public void testGetUserById() {
        long userId = 1L;
        assertNotNull(userDao.getUserById(userId));
    }

    @Test(expected = UserException.class)
    public void testGetUserByIdWithWrongParam() {
        assertNotNull(userDao.getUserById(-1L));
        assertNotNull(userDao.getUserById(0L));
        assertNotNull(userDao.getUserById(100L));
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

    @Test(expected = UserException.class)
    public void testGetUsersByWrongName() {
        User createdUser = userDao.createUser(user);
        createdUser.setName("Google");
        userDao.getUsersByName(createdUser.getName(), 1, 1);
    }

    @Test
    public void testGetUserByEmail() {
        User createdUser = userDao.createUser(user);
        assertNotNull(createdUser.getEmail());

        User receivedUser = userDao.getUserByEmail(createdUser.getEmail());
        assertNotSame(null, receivedUser.getEmail());
        assertEquals(createdUser.getEmail(), receivedUser.getEmail());
    }

    @Test(expected = UserException.class)
    public void testGetUserByWrongEmail() {
        User createdUser = userDao.createUser(user);
        assertNotNull(createdUser.getEmail());
        createdUser.setEmail("wrong@i.ua");
        userDao.getUserByEmail(createdUser.getEmail());
    }
}