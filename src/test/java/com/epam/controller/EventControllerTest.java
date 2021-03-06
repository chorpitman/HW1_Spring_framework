package com.epam.controller;

import com.epam.facade.BookingFacade;
import com.epam.model.Event;
import com.epam.model.impl.EventImpl;
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
public class EventControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingFacade facade;

    private MockMvc mockMvc;

    Event event;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        event = new EventImpl();
    }

    @Test
    public void testCreateEvent() throws Exception {
        event.setTitle("Queen dinner");
        event.setDate(new Date());
        event.setTicketPrice(BigDecimal.ONE);

        event = facade.createEvent(event);

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");

        mockMvc.perform(post("/event/create/")
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(event.getTitle()))
                .andExpect(jsonPath("$.date").value(formatter.format(event.getDate())))
                .andExpect(jsonPath("$.ticketPrice").value(event.getTicketPrice().intValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEventById() throws Exception {
        event.setTitle("Queen dinner");
        event.setDate(new Date());
        event.setTicketPrice(BigDecimal.ONE);

        event = facade.createEvent(event);

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");

        mockMvc.perform(get("/event/get/{id}", event.getId()))
                .andExpect(jsonPath("$.id").value(Math.toIntExact(event.getId())))
                .andExpect(jsonPath("$.title").value(event.getTitle()))
                .andExpect(jsonPath("$.date").value(formatter.format(event.getDate())))
                .andExpect(jsonPath("$.ticketPrice").value(event.getTicketPrice().intValue()))
                .andExpect(status().isOk());
    }

    @Test
    public void testEventNotExists() throws Exception {
        event.setTitle("Queen dinner");
        event.setDate(new Date());
        event.setTicketPrice(BigDecimal.ONE);
        event = facade.createEvent(event);

        mockMvc.perform(get("/event/get/{id}", event.getId() + 100L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetEventsByTitle() throws Exception {
        event.setTitle("Queen dinner");
        event.setDate(new Date());
        event.setTicketPrice(BigDecimal.ONE);

        event = facade.createEvent(event);

        mockMvc.perform(get("/event/title")
                .param("title", event.getTitle())
                .param("pageSize", String.valueOf(1))
                .param("pageNum", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetEventsForDay() throws Exception {
        Event receivedEvent = facade.getEventById(1L);

        mockMvc.perform(get("/event/day")
                .param("day", String.valueOf(receivedEvent.getDate()))
                .param("pageSize", String.valueOf(1))
                .param("pageNum", String.valueOf(1)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateEvent() throws Exception {
        Event receivedEvent = facade.getEventById(1L);

        String newTitle = "King dinner";
        Date newDate = new Date(1499040000000L);
        BigDecimal newPrice = BigDecimal.TEN;

        receivedEvent.setTitle(newTitle);
        receivedEvent.setDate(newDate);
        receivedEvent.setTicketPrice(newPrice);

        mockMvc.perform(put("/event/update/").content(objectMapper.writeValueAsString(receivedEvent))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value((Math.toIntExact(receivedEvent.getId()))))
                .andExpect(jsonPath("$.title").value(newTitle))
                .andExpect(jsonPath("$.date").value(1499040000000L))
                .andExpect(jsonPath("$.ticketPrice").value(newPrice.intValue()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEvent() throws Exception {
        event.setTitle("Queen dinner");
        event.setDate(new Date());
        event.setTicketPrice(BigDecimal.ONE);

        event = facade.createEvent(event);

        mockMvc.perform(delete("/event/delete/{id}", event.getId()))
                .andExpect(content().string("true"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEventWithWrongId() throws Exception {
        event.setTitle("Queen dinner");
        event.setDate(new Date());
        event.setTicketPrice(BigDecimal.ONE);

        event = facade.createEvent(event);

        mockMvc.perform(delete("/event/delete/{id}", event.getId() + 100L))
                .andExpect(status().isNotFound());
    }
}