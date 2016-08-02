package com.epam.controller;

import com.epam.facade.BookingFacade;
import com.epam.model.Event;
import com.epam.model.User;
import com.epam.model.UserAccount;
import com.epam.model.impl.UserAccountImpl;
import com.epam.model.impl.UserImpl;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context.xml")
@WebAppConfiguration
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class UserAccountControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingFacade facade;

    private MockMvc mockMvc;


    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void createUserAccount() throws Exception {
        User user = new UserImpl();
        user.setName("Queen");
        user.setEmail("Queen@i.ua");
        User createdUser = facade.createUser(user);

        UserAccount account = new UserAccountImpl();
        account.setUserId(createdUser.getId());
        account.setAmount(BigDecimal.TEN);

        mockMvc.perform(post("/account/create/").content(objectMapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserAccountById() throws Exception {
        User user = new UserImpl();
        user.setName("Queen");
        user.setEmail("Queen@i.ua");
        User createdUser = facade.createUser(user);

        UserAccount account = new UserAccountImpl();
        account.setUserId(createdUser.getId());
        account.setAmount(BigDecimal.TEN);

        account = facade.createUserAccount(account);

        mockMvc.perform(get("/account/get/{id}", account.getId()))
                .andExpect(jsonPath("$.id").value(Math.toIntExact(account.getId())))
                .andExpect(jsonPath("$.userId").value((Math.toIntExact(account.getUserId()))))
                .andExpect(jsonPath("$.amount").value(account.getAmount().doubleValue()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserAccountByUserId() throws Exception {
        User user = new UserImpl();
        user.setName("Queen");
        user.setEmail("Queen@i.ua");
        User createdUser = facade.createUser(user);
        System.out.println(createdUser);

        System.out.println("createdUser " + facade.getUserById(createdUser.getId()));

        UserAccount account = new UserAccountImpl();
        account.setUserId(createdUser.getId());
        account.setAmount(BigDecimal.TEN);
        account = facade.createUserAccount(account);
        System.out.println(account);

        mockMvc.perform(get("/account/getUserAccountByUserId/{id}", createdUser.getId()))
                .andExpect(jsonPath("$.id").value(Math.toIntExact(account.getId())))
                .andExpect(jsonPath("$.userId").value((Math.toIntExact(account.getUserId()))))
                .andExpect(jsonPath("$.amount").value(account.getAmount().doubleValue()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateUserAccount() throws Exception {
        UserAccount account = facade.getUserAccountById(1L);
        account.setAmount(BigDecimal.ZERO);

        mockMvc.perform(put("/account/update/")
                .content(objectMapper.writeValueAsString(account))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(Math.toIntExact(account.getId())))
                .andExpect(jsonPath("$.userId").value((Math.toIntExact(account.getUserId()))))
                .andExpect(jsonPath("$.amount").value(account.getAmount().intValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUserAccount() throws Exception {
        UserAccount existAccount = facade.getUserAccountById(1L);

        mockMvc.perform(delete("/user/delete/{id}", existAccount.getId()))
                .andExpect(status().isOk());
    }

    @Test(expected = Exception.class)
    public void deleteUserAccountWrongAccount() throws Exception {
        int wrongUserAccount = 100;

        mockMvc.perform(delete("/user/delete/{id}", wrongUserAccount))
                .andExpect(status().isOk());
    }

    @Test
    public void rechargeAccountByAccountId() throws Exception {
        UserAccount account = facade.getUserAccountById(1L);
        account.setAmount(BigDecimal.ZERO);
        System.out.println(account);
        mockMvc.perform(put("/account/rechargeAccountByAccountId/{id}", account.getId()))
//                .content(objectMapper.writeValueAsString(account))
//                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(Math.toIntExact(account.getId())))
                .andExpect(jsonPath("$.userId").value((Math.toIntExact(account.getUserId()))))
                .andExpect(jsonPath("$.amount").value(BigDecimal.ZERO.intValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void rechargeAccountByUserId() throws Exception {

    }

}