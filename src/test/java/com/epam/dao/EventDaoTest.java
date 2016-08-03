package com.epam.dao;

import com.epam.config.ServiceTestConfig;
import com.epam.model.Event;
import com.epam.model.impl.EventImpl;
import com.epam.utils.EventException;
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
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class EventDaoTest {
    private Event event;

    @Autowired
    private EventDao eventDao;

    @Before
    public void init() {
        event = new EventImpl(1L, "Opera", new Date(), BigDecimal.TEN);
    }

    @Test
    public void testGetEventById() {
        assertNotNull(eventDao.getEventById(1L));
    }

    @Test(expected = EventException.class)
    public void testGetEventByIdWithWrongParam() {
        assertNotNull(eventDao.getEventById(-1L));
        assertNotNull(eventDao.getEventById(0L));
        assertNotNull(eventDao.getEventById(100L));
    }

    @Test
    public void testGetEventsByTitle() {
        final String title = "VELODAY";
        List<Event> eventList = eventDao.getEventsByTitle(title, 3, 1);
        assertEquals(3, eventList.size());

        eventList = eventDao.getEventsByTitle(title, 1, 1);
        assertEquals(1, eventList.size());

        eventList = eventDao.getEventsByTitle(title, 1, 2);
        assertEquals(1, eventList.size());

        eventList = eventDao.getEventsByTitle(title, 1, 3);
        assertEquals(1, eventList.size());

        eventList = eventDao.getEventsByTitle(title, 2, 1);
        assertEquals(2, eventList.size());

        eventList = eventDao.getEventsByTitle(title, 2, 2);
        assertEquals(1, eventList.size());
    }

    @Test(expected = EventException.class)
    public void testGetEventsByTitleWrongTitle() {
        final String title = "Moulin Rouge";
        eventDao.getEventsByTitle(title, 1, 1);
    }

    @Test
    public void testGetEventsForDay() {
        eventDao.createEvent(event);
        List<Event> receivedEvent = eventDao.getEventsForDay(event.getDate(), 1, 1);
        assertNotNull(receivedEvent);
        assertEquals(1, receivedEvent.size());
    }

    @Test(expected = EventException.class)
    public void testGetEventsForWrongDay() {
        Event createdEvent = eventDao.createEvent(event);
        createdEvent.setDate(new Date(1499040000000L));
        eventDao.getEventsForDay(new Date(1499040000000L), 1, 1);
    }

    @Test
    public void testCreateEvent() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Event createdEvent = eventDao.createEvent(event);
        assertNotNull(createdEvent);
        assertEquals(dateFormat.format(event.getDate()), dateFormat.format(createdEvent.getDate()));
        assertEquals(event.getTitle(), createdEvent.getTitle());
        assertEquals(event.getTicketPrice(), createdEvent.getTicketPrice());
    }

    @Test
    public void testUpdateEvent() {
        String title = "DnB Party";
        BigDecimal price = BigDecimal.ZERO;
        Date date = new Date();

        Event receivedEvent = eventDao.getEventById(1L);
        assertNotNull(receivedEvent);

        assertNotSame(receivedEvent.getTitle(), title);
        assertNotSame(receivedEvent.getTicketPrice(), price);
        assertNotSame(receivedEvent.getDate(), date);

        receivedEvent.setTitle(title);
        receivedEvent.setDate(date);
        receivedEvent.setTicketPrice(price);

        Event updateEvent = eventDao.updateEvent(receivedEvent);
        assertEquals(title, updateEvent.getTitle());
        assertEquals(price, updateEvent.getTicketPrice());
        assertEquals(date, updateEvent.getDate());
    }

    @Test(expected = EventException.class)
    public void testUpdateEventException() {
        event = new EventImpl(100L, "DnB Party", event.getDate(), BigDecimal.ONE);
        eventDao.updateEvent(event);
    }

    @Test
    public void testDeleteEvent() {
        assertEquals(true, eventDao.deleteEvent(1L));
    }

    @Test(expected = EventException.class)
    public void testDeleteEventWithWrongId() {
        assertEquals(false, eventDao.deleteEvent(-1L));
        assertEquals(false, eventDao.deleteEvent(0L));
        assertEquals(false, eventDao.deleteEvent(100L));
    }

    @Test(expected = EventException.class)
    public void testDeleteNotExistEvent() {
        Event createdEvent = eventDao.createEvent(event);
        assertEquals(createdEvent, eventDao.getEventById(createdEvent.getId()));
        assertEquals(true, eventDao.deleteEvent(createdEvent.getId()));
        eventDao.deleteEvent(createdEvent.getId());
    }
}