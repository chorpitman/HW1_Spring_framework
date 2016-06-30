package com.epam.dao;

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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;


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

    @Test
    public void testGetEventsByTitle() {
        List<Event> eventList = eventDao.getEventsByTitle("VELODAY", 3, 1);
        assertEquals(3, eventList.size());

        eventList = eventDao.getEventsByTitle("VELODAY", 1, 1);
        assertEquals(1, eventList.size());

        eventList = eventDao.getEventsByTitle("VELODAY", 1, 2);
        assertEquals(1, eventList.size());

        eventList = eventDao.getEventsByTitle("VELODAY", 1, 3);
        assertEquals(1, eventList.size());

        eventList = eventDao.getEventsByTitle("VELODAY", 2, 1);
        assertEquals(2, eventList.size());

        eventList = eventDao.getEventsByTitle("VELODAY", 2, 2);
        assertEquals(1, eventList.size());

        eventList = eventDao.getEventsByTitle("VELODAY", 0, 0);
        assertEquals(Collections.EMPTY_LIST, eventList);
    }

    @Test
    public void testGetEventsForDay() {
        eventDao.createEvent(event);
        List<Event> receivedEvent = eventDao.getEventsForDay(event.getDate(), 1, 1);
        assertNotNull(receivedEvent);
        assertEquals(1, receivedEvent.size());
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

    @Test
    public void testDeleteEvent() throws Exception {
        assertEquals(true, eventDao.deleteEvent(1L));
        assertEquals(false, eventDao.deleteEvent(1L));

        assertEquals(false, eventDao.deleteEvent(-1L));
        assertEquals(false, eventDao.deleteEvent(0L));
    }
}