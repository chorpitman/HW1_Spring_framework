package com.epam.dao;

import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import com.epam.storage.EntityStorage;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoImplTest {
    private User user;

    @Autowired
    private EntityStorage storage;

    @Before
    public void init() {
        user = new UserImpl("Oleg", "a@i.ua");
    }

    @Test
    public void testCreateUser() throws Exception {
        storage.createUser(user);

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDeleteUser() throws Exception {

    }

    @Test
    public void testGetUserById() throws Exception {

    }

    @Test
    public void testGetUsersByName() throws Exception {

    }

    @Test
    public void testGetUserByEmail() throws Exception {

    }
}