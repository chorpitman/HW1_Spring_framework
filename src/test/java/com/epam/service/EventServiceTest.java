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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
public class EventServiceTest {

    private Event event;

    @Autowired
    private EventService eventService;

    @Before
    public void init() {
        event = new EventImpl("golf", new Date());
    }

    @Test
    public void testCreateEvent() throws Exception {
        Event createdEvent = eventService.createEvent(event);
        assertNotNull(event);
        assertNotNull(createdEvent);
        assertEquals(event, createdEvent);

        assertNotSame(0, createdEvent.getId());
        assertNotSame(0, createdEvent.getTitle());
        assertNotSame(0, createdEvent.getDate());

        assertEquals(event.getId(), createdEvent.getId());
        assertEquals(event.getTitle(), createdEvent.getTitle());
        assertEquals(event.getDate(), createdEvent.getDate());
    }

    @Test
    public void testUpdateEvent() throws Exception {
        long eventId = event.getId();
        eventService.createEvent(event);
        Event createdEvent = eventService.getEventById(eventId);
        String newTitle = "box";
        Date newDate = new Date();

        createdEvent.setTitle(newTitle);
        createdEvent.setDate(newDate);

        eventService.updateEvent(createdEvent);
        assertEquals(newTitle, event.getTitle());
        assertEquals(newDate, event.getDate());
    }

    @Test
    public void testDeleteEvent() throws Exception {
        long idEvent = event.getId();
        eventService.createEvent(event);
        eventService.deleteEvent(idEvent);
        assertEquals(null, eventService.getEventById(idEvent));
    }

    @Test
    public void testGetEventsForDay() throws Exception {
    }

    @Test
    public void testGetEventsByTitle() throws Exception {
        String title = event.getTitle();
        Event createdEvent = eventService.createEvent(event);

        assertEquals(Collections.emptyList(), eventService.getEventsByTitle(title, 0, 0));
        assertEquals(Collections.emptyList(), eventService.getEventsByTitle(title, 1, 0));
        assertEquals(Collections.emptyList(), eventService.getEventsByTitle(title, 0, 1));
        assertEquals(Arrays.asList(event), eventService.getEventsByTitle(title, 1, 1));
    }

    @Test
    public void getEventsForDay() throws Exception {

    }
}