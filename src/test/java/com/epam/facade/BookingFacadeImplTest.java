package com.epam.facade;

import com.epam.facade.impl.BookingFacadeImpl;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.impl.EventImpl;
import com.epam.model.impl.TicketImpl;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
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
    public void testGetEventById() throws Exception {
        Event event = new EventImpl("figure skating", new Date());
        when(mockEventService.getEventById(event.getId())).thenReturn(event);
        assertEquals(event, bookingFacade.getEventById(event.getId()));
    }

    @Test
    public void testGetEventsByTitle() throws Exception {
        String eventTitle = "figure skating";
        List<Event> eventList = Arrays.asList(new EventImpl(eventTitle, new Date()));

        when(mockEventService.getEventsByTitle(eventTitle, 1, 1)).thenReturn(eventList);
        assertEquals(eventList, bookingFacade.getEventsByTitle(eventTitle, 1, 1));

        when(mockEventService.getEventsByTitle(eventTitle, 0, 0)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsByTitle(eventTitle, 0, 0));

        when(mockEventService.getEventsByTitle(eventTitle, 0, 1)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsByTitle(eventTitle, 0, 1));

        when(mockEventService.getEventsByTitle(eventTitle, 1, 0)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsByTitle(eventTitle, 1, 0));
    }

    @Test
    public void testGetEventsForDay() throws Exception {
        String eventTitle = "figure skating";
        Date date = new Date();

        List<Event> eventList = Arrays.asList(new EventImpl(eventTitle, date));

        when(mockEventService.getEventsForDay(date, 1, 1)).thenReturn(eventList);
        Assert.assertEquals(eventList, bookingFacade.getEventsForDay(date, 1, 1));

        when(mockEventService.getEventsForDay(date, 0, 0)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsForDay(date, 0, 0));

        when(mockEventService.getEventsForDay(date, 1, 0)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsForDay(date, 1, 0));

        when(mockEventService.getEventsForDay(date, 0, 1)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsForDay(date, 0, 1));
    }

    @Test
    public void testCreateEvent() throws Exception {
        String eventTitle = "figure skating";
        Event event = new EventImpl(eventTitle, new Date());

        when(mockEventService.createEvent(event)).thenReturn(event);

        Event createdEvent = bookingFacade.createEvent(event);
        assertNotNull(event);
        assertNotNull(createdEvent);
        assertEquals(event, createdEvent);

        assertEquals(createdEvent.getTitle(), eventTitle);
        assertEquals(createdEvent.getDate(), event.getDate());
    }

    @Test
    public void testUpdateEvent() throws Exception {
        String eventTitle = "hockey";

        Event event = new EventImpl(eventTitle, new Date());
        Event eventUpdt = new EventImpl(eventTitle + " with ball", new Date());

        when(mockEventService.createEvent(event)).thenReturn(event);
        Event createdEvent = bookingFacade.createEvent(event);
        assertNotNull(event);
        assertNotNull(createdEvent);
        assertEquals(event, createdEvent);

        when(mockEventService.updateEvent(eventUpdt)).thenReturn(eventUpdt);

        Event updatedEvent = bookingFacade.updateEvent(eventUpdt);
        assertNotNull(updatedEvent);
        assertNotSame(createdEvent, updatedEvent);
        assertEquals(eventUpdt.getId(), updatedEvent.getId());
        assertEquals(eventUpdt.getTitle(), updatedEvent.getTitle());
        assertEquals(eventUpdt.getDate(), updatedEvent.getDate());
        assertNotSame(event.getDate(), updatedEvent.getDate());
    }

    @Test
    public void testDeleteEvent() throws Exception {
        when(mockEventService.deleteEvent(0L)).thenReturn(Boolean.FALSE);
        assertEquals(bookingFacade.deleteEvent(0L), false);

        when(mockEventService.deleteEvent(1L)).thenReturn(Boolean.TRUE);
        assertEquals(bookingFacade.deleteEvent(1L), true);
    }

    //USER
    @Test
    public void testGetUserById() throws Exception {
        String name = "Joshua Bloch";
        String email = "Joshua_Bloch@i.ua";

        User user = new UserImpl(name, email);
        when(mockUserService.getUserById(user.getId())).thenReturn(user);

        User receivedUser = bookingFacade.getUserById(user.getId());
        assertNotNull(receivedUser);
        assertEquals(receivedUser, user);
        assertEquals(receivedUser.getEmail(), email);
        assertEquals(receivedUser.getName(), name);
    }

    @Test
    public void testGetUserByEmail() throws Exception {
        String name = "Joshua Bloch";
        String email = "Joshua_Bloch@i.ua";
        User user = new UserImpl(name, email);
        when(mockUserService.getUserByEmail(email)).thenReturn(user);
        User receivedUser = bookingFacade.getUserByEmail(email);
        assertEquals(receivedUser.getEmail(), email);
    }

    @Test
    public void testGetUsersByName() throws Exception {
        String userName = "Joshua Bloch";
        String userEmail = "Joshua_Bloch@i.ua";
        List<User> userList = Arrays.asList(new UserImpl(userName, userEmail));

        when(mockUserService.getUsersByName(userName, 1, 1)).thenReturn(userList);
        assertEquals(userList, bookingFacade.getUsersByName(userName, 1, 1));

        when(mockUserService.getUsersByName(userName, 0, 0)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getUsersByName(userName, 0, 0));

        when(mockUserService.getUsersByName(userName, 0, 1)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getUsersByName(userName, 0, 1));

        when(mockUserService.getUsersByName(userName, 1, 0)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getUsersByName(userName, 1, 0));
    }

    @Test
    public void testCreateUser() throws Exception {
        String userName = "Joshua Bloch";
        String userEmail = "Joshua_Bloch@i.ua";
        User user = new UserImpl(userName, userEmail);

        when(mockUserService.createUser(user)).thenReturn(user);
        User createdUser = bookingFacade.createUser(user);
        assertNotNull(user);
        assertNotNull(createdUser);
        assertEquals(user, createdUser);

        assertEquals(createdUser.getName(), userName);
        assertEquals(createdUser.getEmail(), userEmail);
    }

    @Test
    public void testUpdateUser() throws Exception {
        String userName = "Joshua Bloch";
        String userEmail = "Joshua_Bloch@i.ua";

        User user = new UserImpl(userName, userEmail);
        User userUpd = new UserImpl(userName + " Ivanovich", userEmail + ".com");

        when(mockUserService.createUser(user)).thenReturn(user);

        User createdUser = bookingFacade.createUser(user);
        assertNotNull(user);
        assertNotNull(createdUser);
        assertEquals(user, createdUser);

        when(bookingFacade.updateUser(userUpd)).thenReturn(userUpd);
        User receivedUser = bookingFacade.updateUser(userUpd);
        assertNotNull(receivedUser);
        assertNotSame(createdUser, receivedUser);
        assertEquals(userUpd.getId(), receivedUser.getId());
        assertEquals(userUpd.getEmail(), receivedUser.getEmail());
        assertEquals(userUpd.getName(), receivedUser.getName());
    }

    @Test
    public void testDeleteUser() throws Exception {
        //todo спросить у саши как тут проверить
        when(mockUserService.deleteUser(0L)).thenReturn(Boolean.FALSE);
        assertEquals(bookingFacade.deleteUser(0L), false);

        when(mockUserService.deleteUser(1L)).thenReturn(Boolean.TRUE);
        assertEquals(bookingFacade.deleteUser(1L), true);
    }

    //TICKETS
    @Test()
    public void testBookTicket() throws Exception {
        User user = new UserImpl("Joshua Bloch", "Joshua_Bloch@i.ua");
        Event event = new EventImpl("hockey", new Date());
        Ticket ticket = new TicketImpl(Ticket.Category.STANDARD, event.getId(), user.getId(), 1);

        when(mockTicketService.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD)).thenReturn(ticket);
        assertEquals(bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD), ticket);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookTicketException() throws Exception {
        when(mockTicketService.bookTicket(0, 0, 0, Ticket.Category.PREMIUM)).thenThrow(IllegalArgumentException.class);
        bookingFacade.bookTicket(0, 0, 0, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(0, 1, 1, Ticket.Category.PREMIUM);
        bookingFacade.bookTicket(1, 0, 0, Ticket.Category.PREMIUM);
    }

    @Test
    public void getBookedByUserTicket() throws Exception {
        User user = new UserImpl("Joshua Bloch", "Joshua_Bloch@i.ua");
        Event event = new EventImpl("hockey", new Date());
        Ticket ticket = new TicketImpl(Ticket.Category.STANDARD, event.getId(), user.getId(), 1);

//        when(mockTicketService.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD)).thenReturn(ticket);
//        assertEquals(bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD), ticket);

        when(mockTicketService.getBookedTickets(user, 1, 1)).thenReturn(Arrays.asList(ticket));

        assertEquals(bookingFacade.getBookedTickets(user, 1, 1), Arrays.asList(ticket));
        assertEquals(bookingFacade.getBookedTickets(user, 0, 0), Collections.emptyList());
        assertEquals(bookingFacade.getBookedTickets(user, 0, 1), Collections.emptyList());
        assertEquals(bookingFacade.getBookedTickets(user, 1, 0), Collections.emptyList());
    }

    @Test
    public void getBookedTicketsByEventTickets() throws Exception {
        User user = new UserImpl("Joshua Bloch", "Joshua_Bloch@i.ua");
        Event event = new EventImpl("hockey", new Date());
        Ticket ticket = new TicketImpl(Ticket.Category.STANDARD, event.getId(), user.getId(), 1);

        when(mockTicketService.getBookedTickets(event, 1, 1)).thenReturn(Arrays.asList(ticket));

        assertEquals(bookingFacade.getBookedTickets(event, 1, 1), Arrays.asList(ticket));
        assertEquals(bookingFacade.getBookedTickets(event, 0, 0), Collections.emptyList());
        assertEquals(bookingFacade.getBookedTickets(event, 0, 1), Collections.emptyList());
        assertEquals(bookingFacade.getBookedTickets(event, 1, 0), Collections.emptyList());
    }

    @Test
    public void cancelTicket() throws Exception {
        when(mockTicketService.cancelTicket(0L)).thenReturn(Boolean.FALSE);
        assertEquals(bookingFacade.cancelTicket(0L), false);

        when(mockTicketService.cancelTicket(1L)).thenReturn(Boolean.TRUE);
        assertEquals(bookingFacade.cancelTicket(1L), true);
    }
}