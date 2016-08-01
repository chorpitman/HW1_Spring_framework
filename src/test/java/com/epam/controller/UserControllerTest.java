package com.epam.controller;

import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import com.epam.utils.Validator;
import org.junit.Test;

import static org.junit.Assert.*;

import com.epam.facade.BookingFacade;
import com.epam.model.Event;
import com.epam.model.impl.EventImpl;
import com.epam.utils.EventException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context.xml")
@WebAppConfiguration
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class UserControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingFacade facade;

    private MockMvc mockMvc;

    User user;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        user = new UserImpl();
    }

    @Test
    public void testGetUserById() throws Exception {
        user.setEmail("Queen@dinner.com");
        user.setName("Inga");
        user = facade.createUser(user);

        mockMvc.perform(get("/user/get/{id}", user.getId()))
                .andExpect(jsonPath("$.id").value(Math.toIntExact(user.getId())))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test(expected = Exception.class)
    public void testGetUserByIdWrongId() throws Exception {
        user.setEmail("Queen@dinner.com");
        user.setName("Inga");
        user = facade.createUser(user);

        mockMvc.perform(get("/user/get/{id}", user.getId() + 100L))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUser() throws Exception {
        user.setEmail("Queen@dinner.com");
        user.setName("Inga");
        System.out.println(user.getId());
//        facade.createUser(user);
//        System.out.println(user);

        mockMvc.perform(post("/user/create/").content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(Math.toIntExact(user.getId())))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(status().isBadRequest());
    }

    @Test(expected = Exception.class)
    public void testCreateSameUser() throws Exception {
        user.setEmail("Queen@dinner.com");
        user.setName("Inga");
        user = facade.createUser(user);
        mockMvc.perform(post("/user/create/").content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(Math.toIntExact(user.getId())))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUsersByName() throws Exception {
        user.setEmail("Queen@dinner.com");
        user.setName("Inga");
        user = facade.createUser(user);

        mockMvc.perform(get("/user/name")
                .param("name", user.getName())
                .param("pageSize", String.valueOf(1))
                .param("pageNum", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserByEmail() throws Exception {
//        user.setEmail("Queen@dinner.com");
//        user.setName("Inga");
//        user = facade.createUser(user);
//
//        mockMvc.perform(get("/user/email")
//                .param("email", user.getEmail())
//                .andExpect(status().isOk());
    }

    @Test
    public void updateUser() throws Exception {

    }

    @Test
    public void deleteUser() throws Exception {

    }

}