package com.epam.facade;

import com.epam.facade.impl.BookingFacadeImpl;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.UserAccount;
import com.epam.model.impl.EventImpl;
import com.epam.model.impl.TicketImpl;
import com.epam.model.impl.UserAccountImpl;
import com.epam.model.impl.UserImpl;
import com.epam.service.EventService;
import com.epam.service.TicketService;
import com.epam.service.UserAccountService;
import com.epam.service.UserService;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Configuration
@ImportResource("classpath:app-context.xml")
@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class BookingFacadeImplTest {

    @Mock
    private UserService mockUserService;
    @Mock
    private EventService mockEventService;
    @Mock
    private TicketService mockTicketService;
    @Mock
    private UserAccountService mockAccountService;

    private BookingFacade bookingFacade;

    @InjectMocks
    private EventImpl event;

    @InjectMocks
    private UserImpl user;

    @InjectMocks
    private TicketImpl ticket;

    @InjectMocks
    private UserAccountImpl userAccount;

    @Before
    public void init() {
        bookingFacade = new BookingFacadeImpl(mockUserService, mockEventService, mockTicketService, mockAccountService);
    }

    //    EVENT
    @Test
    public void testGetEventById() {
        when(mockEventService.getEventById(event.getId())).thenReturn(event);

        Event recievedEvent = bookingFacade.getEventById(event.getId());
        assertEquals(event.getId(), recievedEvent.getId());
        assertEquals(event, recievedEvent);
    }

    @Test
    public void testGetEventsByTitle() {
        String eventTitle = event.getTitle();
        List<Event> eventList = Arrays.asList(event);

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
    public void testGetEventsForDay() {
        List<Event> eventList = Arrays.asList(event);

        when(mockEventService.getEventsForDay(event.getDate(), 1, 1)).thenReturn(eventList);
        assertEquals(eventList, bookingFacade.getEventsForDay(event.getDate(), 1, 1));

        when(mockEventService.getEventsForDay(event.getDate(), 0, 0)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsForDay(event.getDate(), 0, 0));

        when(mockEventService.getEventsForDay(event.getDate(), 1, 0)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsForDay(event.getDate(), 1, 0));

        when(mockEventService.getEventsForDay(event.getDate(), 0, 1)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getEventsForDay(event.getDate(), 0, 1));
    }

    @Test
    public void testCreateEvent() {
        when(mockEventService.createEvent(event)).thenReturn(event);

        Event createdEvent = bookingFacade.createEvent(event);
        assertNotNull(event);
        assertNotNull(createdEvent);
        assertEquals(event, createdEvent);

        assertEquals(createdEvent.getTitle(), event.getTitle());
        assertEquals(createdEvent.getDate(), event.getDate());
        assertEquals(createdEvent.getId(), event.getId());
    }

    @Test
    public void testUpdateEvent() {
        Event eventUpdt = new EventImpl(event.getTitle() + " with ball", new Date(), new BigDecimal(501));

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
    public void testDeleteEvent() {
        when(mockEventService.deleteEvent(0L)).thenReturn(Boolean.FALSE);
        assertEquals(bookingFacade.deleteEvent(0L), false);

        when(mockEventService.deleteEvent(1L)).thenReturn(Boolean.TRUE);
        assertEquals(bookingFacade.deleteEvent(1L), true);
    }

    //USER
    @Test
    public void testGetUserById() {
        when(mockUserService.getUserById(user.getId())).thenReturn(user);
        User receivedUser = bookingFacade.getUserById(user.getId());

        assertNotNull(receivedUser);
        assertEquals(receivedUser, user);
        assertEquals(receivedUser.getEmail(), user.getEmail());
        assertEquals(receivedUser.getName(), user.getName());
    }

    @Test
    public void testGetUserByEmail() {
        when(mockUserService.getUserByEmail(user.getEmail())).thenReturn(user);
        User receivedUser = bookingFacade.getUserByEmail(user.getEmail());

        assertEquals(receivedUser.getEmail(), user.getEmail());
    }

    @Test
    public void testGetUsersByName() {
        int pageSize = 1;
        int pageNum = 1;

        List<User> userList = Arrays.asList(user);

        when(mockUserService.getUsersByName(user.getName(), pageSize, pageNum)).thenReturn(userList);
        assertEquals(userList, bookingFacade.getUsersByName(user.getName(), pageSize, pageNum));

        pageSize = 0;
        pageNum = 0;

        when(mockUserService.getUsersByName(user.getName(), pageSize, pageNum)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getUsersByName(user.getName(), 0, 0));

        pageSize = 0;
        pageNum = 1;

        when(mockUserService.getUsersByName(user.getName(), pageSize, pageNum)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getUsersByName(user.getName(), pageSize, pageNum));

        pageSize = 1;
        pageNum = 0;

        when(mockUserService.getUsersByName(user.getName(), pageSize, pageNum)).thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(), bookingFacade.getUsersByName(user.getName(), pageSize, pageNum));
    }

    @Test
    public void testCreateUser() {
        when(mockUserService.createUser(user)).thenReturn(user);
        User createdUser = bookingFacade.createUser(user);

        assertNotNull(user);
        assertNotNull(createdUser);
        assertEquals(user, createdUser);
        assertEquals(createdUser.getName(), user.getName());
        assertEquals(createdUser.getEmail(), user.getEmail());
    }

    @Test
    public void testUpdateUser() {
        when(mockUserService.createUser(user)).thenReturn(user);
        User createdUser = bookingFacade.createUser(user);

        assertNotNull(user);
        assertNotNull(createdUser);
        assertEquals(user, createdUser);

        String userName = "Joshua Bloch";
        String userEmail = "Joshua_Bloch@i.ua";
        User userUpd = new UserImpl(userName, userEmail);

        when(bookingFacade.updateUser(userUpd)).thenReturn(userUpd);
        User receivedUser = bookingFacade.updateUser(userUpd);

        assertNotNull(receivedUser);
        assertNotSame(createdUser, receivedUser);
        assertEquals(userUpd.getId(), receivedUser.getId());
        assertEquals(userUpd.getEmail(), receivedUser.getEmail());
        assertEquals(userUpd.getName(), receivedUser.getName());
    }

    @Test
    public void testDeleteUser() {
        when(mockUserService.deleteUser(0L)).thenReturn(Boolean.FALSE);
        assertEquals(bookingFacade.deleteUser(0L), false);

        when(mockUserService.deleteUser(1L)).thenReturn(Boolean.TRUE);
        assertEquals(bookingFacade.deleteUser(1L), true);
    }

    //TICKETS
    @Test()
    public void testBookTicket() {
        when(mockTicketService.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD)).thenReturn(ticket);
        assertEquals(bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD), ticket);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookTicketException() {
        long userId = 0;
        long eventId = 0;
        int place = 0;

        when(mockTicketService.bookTicket(userId, eventId, place, Ticket.Category.PREMIUM)).thenThrow(IllegalArgumentException.class);
        bookingFacade.bookTicket(userId, eventId, place, Ticket.Category.PREMIUM);

        userId = 0;
        eventId = 1;
        place = 1;
        bookingFacade.bookTicket(userId, eventId, place, Ticket.Category.PREMIUM);

        userId = 1;
        eventId = 0;
        place = 0;
        when(mockTicketService.bookTicket(userId, eventId, place, Ticket.Category.PREMIUM)).thenThrow(IllegalArgumentException.class);
        bookingFacade.bookTicket(userId, eventId, place, Ticket.Category.PREMIUM);
    }

    @Test
    public void getBookedByUserTicket() {
        int pageSize = 1;
        int pageNum = 1;
        when(mockTicketService.getBookedTickets(user, pageSize, pageNum)).thenReturn(Arrays.asList(ticket));
        assertEquals(bookingFacade.getBookedTickets(user, 1, 1), Arrays.asList(ticket));

        pageSize = 0;
        pageNum = 0;
        assertEquals(bookingFacade.getBookedTickets(user, pageSize, pageNum), Collections.emptyList());

        pageSize = 0;
        pageNum = 1;
        assertEquals(bookingFacade.getBookedTickets(user, pageSize, pageNum), Collections.emptyList());

        pageSize = 1;
        pageNum = 0;
        assertEquals(bookingFacade.getBookedTickets(user, pageSize, pageNum), Collections.emptyList());
    }

    @Test
    public void getBookedTicketsByEventTickets() {
        int pageSize = 1;
        int pageNum = 1;
        when(mockTicketService.getBookedTickets(event, pageSize, pageNum)).thenReturn(Arrays.asList(ticket));
        assertEquals(bookingFacade.getBookedTickets(event, 1, 1), Arrays.asList(ticket));

        pageSize = 0;
        pageNum = 0;
        assertEquals(bookingFacade.getBookedTickets(event, pageSize, pageNum), Collections.emptyList());

        pageSize = 0;
        pageNum = 1;
        assertEquals(bookingFacade.getBookedTickets(event, pageSize, pageNum), Collections.emptyList());

        pageSize = 1;
        pageNum = 0;
        assertEquals(bookingFacade.getBookedTickets(event, pageSize, pageNum), Collections.emptyList());
    }

    @Test
    public void cancelTicket() {
        when(mockTicketService.cancelTicket(0L)).thenReturn(Boolean.FALSE);
        assertEquals(bookingFacade.cancelTicket(0L), false);

        when(mockTicketService.cancelTicket(1L)).thenReturn(Boolean.TRUE);
        assertEquals(bookingFacade.cancelTicket(1L), true);
    }

    //USER ACCOUNT
    @Test
    public void testCreateUserAccount() {
        when(mockAccountService.createUserAccount(userAccount)).thenReturn(userAccount);
        UserAccount createdUserAccount = bookingFacade.createUserAccount(userAccount);

        assertNotNull(userAccount);
        assertNotNull(createdUserAccount);
        assertEquals(userAccount, createdUserAccount);
        assertEquals(userAccount.getAmount(), createdUserAccount.getAmount());
    }

    @Test
    public void testGetUserAccountById() {
        when(mockAccountService.getUserAccountById(userAccount.getId())).thenReturn(userAccount);
        UserAccount receivedUserAccount = bookingFacade.getUserAccountById(userAccount.getId());

        assertEquals(userAccount.getUserId(), receivedUserAccount.getUserId());
        assertEquals(userAccount.getAmount(), receivedUserAccount.getAmount());
        assertEquals(userAccount, receivedUserAccount);
    }

    @Test
    public void testGetUserAccountByUserId() {
        userAccount.setUserId(1);

        when(mockAccountService.getUserAccountByUserId(userAccount.getUserId())).thenReturn(userAccount);
        UserAccount receivedUserAccount = bookingFacade.getUserAccountByUserId(userAccount.getUserId());

        assertEquals(userAccount.getAmount(), receivedUserAccount.getAmount());
        assertEquals(userAccount.getId(), receivedUserAccount.getId());
        assertEquals(userAccount.getUserId(), receivedUserAccount.getUserId());
    }

    @Test
    public void testUpdateUserAccount() {
        userAccount.setId(1);
        BigDecimal updateBalance = new BigDecimal(3000);
        when(mockAccountService.getUserAccountById(userAccount.getId())).thenReturn(userAccount);
        UserAccount receivedUserAccount = bookingFacade.getUserAccountById(userAccount.getId());
        verify(mockAccountService).getUserAccountById(userAccount.getId());

        when(mockAccountService.updateUserAccount(receivedUserAccount)).thenReturn(receivedUserAccount);
        receivedUserAccount.setAmount(updateBalance);

        assertTrue(bookingFacade.getUserAccountById((userAccount.getId())).getAmount().compareTo(updateBalance) == 0);
    }

    @Test
    public void testDeleteUserAccount() {
        when(mockAccountService.deleteUserAccount(-1L)).thenReturn(Boolean.FALSE);
        assertEquals(false, bookingFacade.deleteUserAccount(-1L));

        when(mockUserService.deleteUser(0L)).thenReturn(Boolean.FALSE);
        assertEquals(false, bookingFacade.deleteUserAccount(0L));

        when(mockAccountService.deleteUserAccount(1L)).thenReturn(Boolean.TRUE);
        assertEquals(true, bookingFacade.deleteUserAccount(1L));
    }

    @Test
    public void testRechargeAccountByAccountId() {
        bookingFacade.rechargeAccountByAccountId(userAccount.getId(), BigDecimal.TEN);
        verify(mockAccountService).rechargeAccountByAccountId(userAccount.getId(), BigDecimal.TEN);
    }

    @Test
    public void testRechargeAccountByUserId() {
        userAccount.setUserId(1L);
        bookingFacade.rechargeAccountByUserId(userAccount.getUserId(), BigDecimal.TEN);
        verify(mockAccountService).rechargeAccountByUserId(userAccount.getUserId(), BigDecimal.TEN);
    }
}