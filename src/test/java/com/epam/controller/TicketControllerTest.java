package com.epam.controller;

import com.epam.model.Ticket;
import com.epam.model.impl.TicketImpl;
import org.junit.Test;

import static org.junit.Assert.*;

import com.epam.facade.BookingFacade;
import com.epam.model.User;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context.xml")
@WebAppConfiguration
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class TicketControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingFacade facade;

    private MockMvc mockMvc;

    Ticket ticket;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        ticket = new TicketImpl();
    }

    @Test
    public void testBookTicket() throws Exception {
        ticket.setCategory(Ticket.Category.BAR);
        ticket.setPlace(5);
        ticket.setUserId(1);
        ticket.setEventId(1);
                System.out.println(user.getId());

        mockMvc.perform(post("/user/create/").content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookedTickets() throws Exception {

    }

    @Test
    public void testGetBookedTickets1() throws Exception {

    }

    @Test
    public void testCancelTicket() throws Exception {

    }

}