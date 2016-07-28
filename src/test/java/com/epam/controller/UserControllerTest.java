//package com.epam.controller;
//
//import com.epam.facade.BookingFacade;
//import com.epam.model.User;
//import com.epam.model.impl.UserImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:root-context.xml")
//@WebAppConfiguration
//@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
//public class UserControllerTest {
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private BookingFacade facade;
//
//    private MockMvc mockMvc;
//
//    User user;
//
//
//    @Test
//    public void getUserById() throws Exception {
//
//    }
//
//    @Test
//    public void createUser() throws Exception {
//
//    }
//
//    @Test
//    public void getUsersByName() throws Exception {
//
//    }
//
//    @Test
//    public void getUserByEmail() throws Exception {
//        user = new UserImpl();
//        user.setId(1L);
//        user.setName("Name");
//        user.setEmail("i@ua");
//        facade.createUser(user);
//
//        mockMvc.perform(get("/user/email/", user.getEmail()))
//                .andExpect(jsonPath("$.name", new Object[0]).value(user.getName()))
//                .andExpect(jsonPath("$.email", new Object[0]).value(user.getEmail()))
//                .andExpect(status().isOk());
//    }
//
//
//    @Test
//    public void updateUser() throws Exception {
//
//    }
//
//    @Test
//    public void deleteUser() throws Exception {
//
//    }
//
//}