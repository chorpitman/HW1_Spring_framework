package com.epam.service;

import com.epam.dao.EventDao;
import com.epam.model.Event;
import com.epam.model.impl.EventImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Configuration
@ImportResource("classpath:app-context.xml")
@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {
    @InjectMocks
    private EventImpl event;

    @Mock
    private EventDao mockEventDao;

    @Before
    public void init() {
        event = new EventImpl(1L, "golf", new Date(), BigDecimal.TEN);
    }

    @Test
    public void testGetEventById() {
        when(mockEventDao.createEvent(event)).thenReturn(event);
        Event createdEvent = mockEventDao.createEvent(event);
        verify(mockEventDao).createEvent(event);

        assertNotSame(null, createdEvent.getId());
        assertEquals(createdEvent.getTitle(), event.getTitle());
        assertEquals(createdEvent.getTicketPrice(), event.getTicketPrice());

        when(mockEventDao.getEventById(event.getId())).thenReturn(event);
        Event receivedEvent = mockEventDao.getEventById(createdEvent.getId());
        verify(mockEventDao).getEventById(event.getId());

        assertEquals(event.getTitle(), receivedEvent.getTitle());
        assertEquals(event.getTicketPrice(), receivedEvent.getTicketPrice());
        assertEquals(event.getDate(), receivedEvent.getDate());
        assertEquals(createdEvent, receivedEvent);
    }

    @Test
    public void testCreateEvent() {
        when(mockEventDao.createEvent(event)).thenReturn(event);
        Event createdEvent = mockEventDao.createEvent(event);
        verify(mockEventDao).createEvent(event);
        assertNotNull(createdEvent);
        assertEquals(event, createdEvent);
    }

    @Test
    public void testUpdateEvent() {
        when(mockEventDao.createEvent(event)).thenReturn(event);
        Event createdEvent = mockEventDao.createEvent(event);
        verify(mockEventDao).createEvent(event);

        String title = "box";
        Date date = new Date();

        createdEvent.setDate(date);
        createdEvent.setTitle(title);
        createdEvent.setTicketPrice(BigDecimal.ONE);

        when(mockEventDao.updateEvent(event)).thenReturn(event);
        Event receivedEvent = mockEventDao.updateEvent(createdEvent);
        assertEquals(createdEvent, receivedEvent);
    }

    @Test
    public void testDeleteEvent() {
        long eventId = 2L;
        when(mockEventDao.deleteEvent(eventId)).thenReturn(Boolean.TRUE);
        assertEquals(true, mockEventDao.deleteEvent(eventId));
        assertEquals(false, mockEventDao.deleteEvent(0));
        assertEquals(false, mockEventDao.deleteEvent(-1));
        assertEquals(false, mockEventDao.deleteEvent(100));
        verify(mockEventDao).deleteEvent(eventId);
    }

    @Test
    public void testGetEventsByTitle() {
        when(mockEventDao.getEventsByTitle(event.getTitle(), 1, 1)).thenReturn(Arrays.asList(event));
        List<Event> receivedUser = mockEventDao.getEventsByTitle(event.getTitle(), 1, 1);
        verify(mockEventDao).getEventsByTitle(event.getTitle(), 1, 1);

        assertNotNull(receivedUser);
        assertEquals(event.getTitle(), receivedUser.get(0).getTitle());
        assertTrue(receivedUser.containsAll(Arrays.asList(event)));
    }

    @Test
    public void testGetEventsForDay() {
        when(mockEventDao.getEventsForDay(event.getDate(), 1, 1)).thenReturn(Arrays.asList(event));
        List<Event> receivedUser = mockEventDao.getEventsForDay(event.getDate(), 1, 1);
        verify(mockEventDao).getEventsForDay(event.getDate(), 1, 1);

        assertNotNull(receivedUser);
        assertEquals(event.getDate(), receivedUser.get(0).getDate());
        assertTrue(receivedUser.containsAll(Arrays.asList(event)));
    }
}