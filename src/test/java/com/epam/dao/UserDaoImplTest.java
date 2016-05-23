package com.epam.dao;

import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import com.epam.storage.EntityStorage;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

@Configuration
@ImportResource("classpath:app-context.xml")
@Profile("test")
public class UserDaoImplTest {

    @Autowired
    private EntityStorage storage;


    @Test
    public void testCreateUser() throws Exception {
        User user = new UserImpl("sdas", "ddddd");
            storage.createUser(user);
    }

//    @Test
//    public void testUpdate() throws Exception {
//
//    }

//    @Test
//    public void testDeleteUser() throws Exception {
//
//    }

//    @Test
//    public void testGetUserById() throws Exception {
//
//    }
//
//    @Test
//    public void testGetUsersByName() throws Exception {
//
//    }
//
//    @Test
//    public void testGetUserByEmail() throws Exception {
//
//    }
}