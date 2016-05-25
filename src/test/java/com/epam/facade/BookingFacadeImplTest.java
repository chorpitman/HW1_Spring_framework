package com.epam.facade;

import com.epam.facade.impl.BookingFacadeImpl;
import com.epam.model.Event;
import com.epam.model.User;
import com.epam.model.impl.EventImpl;
import com.epam.model.impl.UserImpl;
import com.epam.service.impl.EventServiceImpl;
import com.epam.service.impl.TicketServiceImpl;
import com.epam.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@ImportResource("classpath:app-context.xml")
@Profile("test")
public class BookingFacadeImplTest {

    private BookingFacadeImpl bookingFacade;

    private UserServiceImpl mockUserService;
    private EventServiceImpl mockEventService;
    private TicketServiceImpl mockTicketService;

    @Before
    public void init() {
        mockUserService = mock(UserServiceImpl.class);
        mockEventService = mock(EventServiceImpl.class);
        mockTicketService = mock(TicketServiceImpl.class);
        bookingFacade = new BookingFacadeImpl(mockUserService, mockEventService, mockTicketService);
    }

    //    EVENT
    @Test
    public void getEventById() throws Exception {
        Event event = new EventImpl("figure skating", new Date());
        when(mockEventService.getEventById(event.getId())).thenReturn(event);
        assertEquals(event, bookingFacade.getEventById(event.getId()));
    }

    @Test
    public void getEventsByTitle() throws Exception {
        List<Event> eventList = new ArrayList<>();

        when(mockEventService.getEventsByTitle("figure skating", 1, 1)).thenReturn(eventList);
        assertEquals(eventList, bookingFacade.getEventsByTitle("figure skating", 1, 1));

        when(mockEventService.getEventsByTitle("figure skating", 0, 0)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsByTitle("figure skating", 0, 0));

        when(mockEventService.getEventsByTitle("figure skating", 0, 1)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsByTitle("figure skating", 0, 1));

        when(mockEventService.getEventsByTitle("figure skating", 1, 0)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsByTitle("figure skating", 1, 0));
    }

    @Test
    public void getEventsForDay() throws Exception {

    }

    @Test
    public void createEvent() throws Exception {

    }

    @Test
    public void updateEvent() throws Exception {

    }

    @Test
    public void deleteEvent() throws Exception {

    }

    //USER
    @Test
    public void getUserById() throws Exception {

    }

    @Test
    public void getUserByEmail() throws Exception {

    }

    @Test
    public void getUsersByName() throws Exception {

    }

    @Test
    public void createUser() throws Exception {

    }

    @Test
    public void updateUser() throws Exception {

    }

    @Test
    public void deleteUser() throws Exception {

    }

    //    TICKETS
    @Test
    public void bookTicket() throws Exception {

    }

    @Test
    public void getBookedTickets() throws Exception {

    }

    @Test
    public void getBookedTickets1() throws Exception {

    }

    @Test
    public void cancelTicket() throws Exception {

    }
}