package com.epam.controller;

import com.epam.facade.BookingFacade;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.UserAccount;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private User user;

    private Event event;

    private UserAccount account;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testBookTicket() throws Exception {
        user = facade.getUserById(1L);
        event = facade.getEventById(1L);
        facade.rechargeAccountByUserId(user.getId(), new BigDecimal(100));

        mockMvc.perform(post("/ticket/bookTicket/")
                .param("userId", String.valueOf(1L))
                .param("eventId", String.valueOf(1L))
                .param("place", String.valueOf(1))
                .param("category", String.valueOf(Ticket.Category.BAR)))
                .andExpect(status().isOk());
    }

    @Test
    public void testBookTicketDeficitUserBalance() throws Exception {
        user = facade.getUserById(1L);
        event = facade.getEventById(1L);

        mockMvc.perform(post("/ticket/bookTicket/")
                .param("userId", String.valueOf(1L))
                .param("eventId", String.valueOf(1L))
                .param("place", String.valueOf(1))
                .param("category", String.valueOf(Ticket.Category.BAR)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetBookedTicketsByUser() throws Exception {
        user = facade.getUserById(1L);

        mockMvc.perform(get("/ticket/bookedUserTicket/")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageSize", String.valueOf(1L))
                .param("pageNum", String.valueOf(1L)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookedTicketsByUserWrongUserId() throws Exception {
        User wrongUserId = facade.getUserById(4L);

        mockMvc.perform(get("/ticket/bookedUserTicket/")
                .content(objectMapper.writeValueAsString(wrongUserId))
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageSize", String.valueOf(1L))
                .param("pageNum", String.valueOf(1L)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetBookedTicketsByEvent() throws Exception {
        event = facade.getEventById(1L);

        mockMvc.perform(get("/ticket/bookedEventTicket/")
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageSize", String.valueOf(1L))
                .param("pageNum", String.valueOf(1L)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookedTicketsByEventWrongEventId() throws Exception {
        Event wrongEvent = facade.getEventById(4L);

        mockMvc.perform(get("/ticket/bookedEventTicket/")
                .content(objectMapper.writeValueAsString(wrongEvent))
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageSize", String.valueOf(1L))
                .param("pageNum", String.valueOf(1L)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCancelTicket() throws Exception {
        int existTicketId = 1;
        mockMvc.perform(delete("/ticket//cancel/{id}", existTicketId))
                .andExpect(content().string("true"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCancelTicketWrongId() throws Exception {
        int nonExistTicketId = 10;
        mockMvc.perform(delete("/ticket//cancel/{id}", nonExistTicketId))
                .andExpect(status().isNotFound());
    }
}