package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.model.Event;
import com.epam.model.impl.EventImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class EventServiceTest {

    private Event event;

    @Autowired
    private EventService eventService;

    @Before
    public void init() {
        event = new EventImpl(1L, "golf", new Date(), new BigDecimal(1500));
    }

    @Test
    public void testGetEventById() {
        Event createdEvent = eventService.createEvent(event);
        Event receivedEvent = eventService.getEventById(createdEvent.getId());
        assertEquals(createdEvent.getId(), receivedEvent.getId());
    }

    @Test
    public void testCreateEvent() {
        Event createdEvent = eventService.createEvent(event);
        assertNotNull(event);
        assertNotNull(createdEvent);

        Event receivedEvent = eventService.getEventById(createdEvent.getId());
        assertEquals(receivedEvent.getDate(), createdEvent.getDate());
        assertEquals(receivedEvent.getTitle(), createdEvent.getTitle());
        assertEquals(receivedEvent.getTicketPrice(), createdEvent.getTicketPrice());
    }

    @Test
    public void testUpdateEvent() {
        Event createdEvent = eventService.createEvent(event);
        String newTitle = "box";
        Date newDate = new Date();

        createdEvent.setDate(newDate);
        createdEvent.setTitle(newTitle);
        createdEvent.setTicketPrice(new BigDecimal("987"));

        Event receivedEvent = eventService.updateEvent(createdEvent);
        assertEquals(createdEvent, receivedEvent);
    }

    @Test
    public void testDeleteEvent() {
        Event createdEvent = eventService.createEvent(event);
        assertEquals(true, eventService.deleteEvent(createdEvent.getId()));
    }

    @Test
    public void testGetEventsByTitle() {
        String title = event.getTitle();
        Event createdEvent = eventService.createEvent(event);

        assertEquals(title, createdEvent.getTitle());
        assertEquals(Arrays.asList(createdEvent), eventService.getEventsByTitle(title, 1, 1));
    }

    @Test
    public void testGetEventsForDay() {
        Date date = event.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Event createdEvent = eventService.createEvent(event);

        assertEquals(dateFormat.format(date), dateFormat.format(createdEvent.getDate()));
        assertEquals(Arrays.asList(createdEvent), eventService.getEventsForDay(date, 1, 1));
    }
}