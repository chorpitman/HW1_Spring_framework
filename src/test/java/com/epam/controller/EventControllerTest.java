package com.epam.controller;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:root-context.xml")
@WebAppConfiguration
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class EventControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    Event event;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetEventById() {
    }

    @Test
    public void testGetEventsByTitle() {
    }

    @Test
    public void testGetEventsForDay() throws Exception {

    }

    @Test
    public void testCreateEvent() throws Exception {
        event = new EventImpl(1L, "Dinner with Queen", new Date(), BigDecimal.ONE);
        mockMvc.perform(MockMvcRequestBuilders.post("/event/create/").content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateEvent() throws Exception {
        event = new EventImpl(1L, "Dinner with Queen", new Date(), BigDecimal.ONE);
        mockMvc.perform(MockMvcRequestBuilders.put("/event/update/").content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEvent() throws Exception {
        long eventId = 1L;
        mockMvc.perform(delete("/event/delete/").param("id", String.valueOf(eventId)))
                .andExpect(status().isOk());
    }
}