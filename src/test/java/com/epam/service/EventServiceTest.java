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
        assertNotNull(createdEvent);

        assertNotSame(0, createdEvent.getId());
        assertNotSame(0, createdEvent.getTitle());
        assertNotSame(0, createdEvent.getDate());

        assertEquals(event.getId(), createdEvent.getId());
        assertEquals(event.getTitle(), createdEvent.getTitle());
        assertEquals(event.getDate(), createdEvent.getDate());
    }

    @Test
    public void updateEvent() throws Exception {
        final String eventTitle= "figure skating";
        final Date date = new Date();
        event.setTitle(eventTitle);
        event.setDate(date);

        Event updEvent = eventService.updateEvent(event);
 //       assertEquals(eventTitle, updEvent.getTitle());
        assertEquals(date, updEvent.getDate());
    }

    @Test
    public void deleteEvent() throws Exception {
        long idEvent = event.getId();
        eventService.createEvent(event);
        eventService.deleteEvent(event.getId());
        assertEquals(null, eventService.getEventById(idEvent));
    }

    @Test
    public void getEventsForDay() throws Exception {

    }


}