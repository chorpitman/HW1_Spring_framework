package com.epam.controller;

import com.epam.model.Event;
import com.epam.model.impl.EventImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Configuration
@ImportResource("classpath:app-context.xml")
@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

    @InjectMocks
    EventImpl event;

    @Mock
    EventController mockEventController;

    @Test
    public void testGetEventById() {
        when(mockEventController.getEventById(event.getId())).thenReturn(event);
        mockEventController.getEventById(event.getId());
        verify(mockEventController).getEventById(event.getId());
    }

    @Test
    public void testGetEventsByTitle()  {
        event = new EventImpl(1L, "title", new Date(), BigDecimal.TEN);
        List<Event> eventList = Arrays.asList(event);
        when(mockEventController.getEventsByTitle(event.getTitle(), 1, 1)).thenReturn(eventList);
        assertEquals(eventList, mockEventController.getEventsByTitle(event.getTitle(), 1, 1));
        verify(mockEventController).getEventsByTitle(event.getTitle(), 1, 1);
    }

    @Test
    public void testGetEventsForDay() throws Exception {

    }

    @Test
    public void testCreateEvent() throws Exception {

    }

    @Test
    public void testUpdateEvent() throws Exception {

    }

    @Test
    public void testDeleteEvent() throws Exception {

    }
}