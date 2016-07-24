package com.epam.facade;

import com.epam.config.ServiceTestConfig;
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
import org.springframework.test.context.ContextConfiguration;

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
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
//@ImportResource("classpath:app-context.xml")
@Profile("test")
public class BookingFacadeImplTest {

    private BookingFacade bookingFacade;
    @InjectMocks
    private EventImpl event;
    @InjectMocks
    private UserImpl user;
    @InjectMocks
    private TicketImpl ticket;
    @InjectMocks
    private UserAccountImpl userAccount;

    @Mock
    private UserService mockUserService;

    @Mock
    private EventService mockEventService;

    @Mock
    private TicketService mockTicketService;

    @Mock
    private UserAccountService mockAccountService;

    @Before
    public void init() {
        bookingFacade = new BookingFacadeImpl(mockUserService, mockEventService, mockTicketService, mockAccountService);
        user = new UserImpl(1L, "Dexter", "Dexter@i.ua");
        event = new EventImpl(1L, "Basketball", new Date(), BigDecimal.TEN);
        userAccount = new UserAccountImpl(1L, 1L, BigDecimal.ZERO);
    }

    //    EVENT
    @Test
    public void testGetEventById() {
        when(mockEventService.getEventById(event.getId())).thenReturn(event);

        Event receivedEvent = bookingFacade.getEventById(event.getId());
        assertEquals(event.getId(), receivedEvent.getId());
        assertEquals(event, receivedEvent);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEventByIdWithWrongParam() {
        bookingFacade.getEventById(0L);
        verify(mockEventService).getEventById(0L);
        bookingFacade.getEventById(-1L);
        verify(mockEventService).getEventById(-1L);
    }

    @Test
    public void testGetEventsByTitle() {
        List<Event> eventList = Arrays.asList(event);

        when(mockEventService.getEventsByTitle(event.getTitle(), 1, 1)).thenReturn(eventList);
        assertEquals(eventList, bookingFacade.getEventsByTitle(event.getTitle(), 1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEventsByTitleWithWrongParam() {
        String eventTitle = event.getTitle();
        bookingFacade.getEventsByTitle(eventTitle, 0, 0);
        verify(mockEventService).getEventsByTitle(eventTitle, 0, 0);

        bookingFacade.getEventsByTitle(eventTitle, 0, 1);
        verify(mockEventService).getEventsByTitle(eventTitle, 0, 1);

        bookingFacade.getEventsByTitle(eventTitle, 1, 0);
        verify(mockEventService).getEventsByTitle(eventTitle, 1, 0);

        bookingFacade.getEventsByTitle(eventTitle, -1, -1);
        verify(mockEventService).getEventsByTitle(eventTitle, -1, -1);

        bookingFacade.getEventsByTitle(eventTitle, 1, -1);
        verify(mockEventService).getEventsByTitle(eventTitle, 1, -1);

        bookingFacade.getEventsByTitle(eventTitle, -1, 1);
        verify(mockEventService).getEventsByTitle(eventTitle, -1, 1);
    }

    @Test
    public void testGetEventsForDay() {
        List<Event> eventList = Arrays.asList(event);

        when(mockEventService.getEventsForDay(event.getDate(), 1, 1)).thenReturn(eventList);
        assertEquals(eventList, bookingFacade.getEventsForDay(event.getDate(), 1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEventsForDayWithWrongParam() {
        bookingFacade.getEventsForDay(event.getDate(), 0, 0);
        verify(mockEventService).getEventsForDay(event.getDate(), 0, 0);

        bookingFacade.getEventsForDay(event.getDate(), 1, 0);
        verify(mockEventService).getEventsForDay(event.getDate(), 1, 0);

        bookingFacade.getEventsForDay(event.getDate(), 0, 1);
        verify(mockEventService).getEventsForDay(event.getDate(), 0, 1);

        bookingFacade.getEventsForDay(event.getDate(), -1, -1);
        verify(mockEventService).getEventsForDay(event.getDate(), -1, -1);

        bookingFacade.getEventsForDay(event.getDate(), 1, -1);
        verify(mockEventService).getEventsForDay(event.getDate(), 1, -1);

        bookingFacade.getEventsForDay(event.getDate(), -1, 1);
        verify(mockEventService).getEventsForDay(event.getDate(), -1, 1);
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

    @Test(expected = IllegalArgumentException.class)
    public void testCreateEventWithWrongParam() {
        bookingFacade.createEvent(null);
        verify(mockEventService).createEvent(null);
    }

    @Test
    public void testUpdateEvent() {
        Event eventForUpdate = new EventImpl(3L, event.getTitle() + " with ball", new Date(), BigDecimal.TEN);

        when(mockEventService.createEvent(event)).thenReturn(event);

        Event createdEvent = bookingFacade.createEvent(event);
        assertNotNull(event);
        assertNotNull(createdEvent);
        assertEquals(event, createdEvent);

        when(mockEventService.updateEvent(eventForUpdate)).thenReturn(eventForUpdate);

        Event updatedEvent = bookingFacade.updateEvent(eventForUpdate);
        assertNotNull(updatedEvent);
        assertNotSame(createdEvent, updatedEvent);
        assertEquals(eventForUpdate.getId(), updatedEvent.getId());
        assertEquals(eventForUpdate.getTitle(), updatedEvent.getTitle());
        assertEquals(eventForUpdate.getDate(), updatedEvent.getDate());
        assertNotSame(event.getDate(), updatedEvent.getDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEventWithWrongParam() {
        bookingFacade.updateEvent(null);
        verify(mockEventService).updateEvent(null);
    }

    @Test
    public void testDeleteEvent() {
        when(mockEventService.deleteEvent(1L)).thenReturn(Boolean.TRUE);
        assertEquals(bookingFacade.deleteEvent(1L), true);
        verify(mockEventService).deleteEvent(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteEventWithWrongParam() {
        assertTrue(bookingFacade.deleteEvent(0L));
        verify(mockEventService.deleteEvent(0L));

        assertTrue(bookingFacade.deleteEvent(-1L));
        verify(mockEventService.deleteEvent(-1L));
    }


    //USER
    @Test
    public void testGetUserById() {
        when(mockUserService.getUserById(user.getId())).thenReturn(user);
        User receivedUser = bookingFacade.getUserById(user.getId());
        verify(mockUserService).getUserById(user.getId());

        assertNotNull(receivedUser);
        assertEquals(receivedUser, user);
        assertEquals(receivedUser.getEmail(), user.getEmail());
        assertEquals(receivedUser.getName(), user.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByIdWithWrongParam() {
        bookingFacade.getUserById(0L);
        verify(mockUserService).getUserById(0L);

        bookingFacade.getUserById(-1L);
        verify(mockUserService).getUserById(-1L);
    }

    @Test
    public void testGetUserByEmail() {
        when(mockUserService.getUserByEmail(user.getEmail())).thenReturn(user);
        User receivedUser = bookingFacade.getUserByEmail(user.getEmail());
        verify(mockUserService).getUserByEmail(user.getEmail());

        assertEquals(receivedUser.getEmail(), user.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserByEmailWithWrongParam() {
        bookingFacade.getUserByEmail(null);
        verify(mockUserService).getUserByEmail(null);
    }

    @Test
    public void testGetUsersByName() {
        int pageSize = 1;
        int pageNum = 1;

        List<User> userList = Arrays.asList(user);

        when(mockUserService.getUsersByName(user.getName(), pageSize, pageNum)).thenReturn(userList);
        assertEquals(userList, bookingFacade.getUsersByName(user.getName(), pageSize, pageNum));
        verify(mockUserService).getUsersByName(user.getName(), pageSize, pageNum);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUsersByNameWithWrongParam() {
        int pageSize = 1;
        int pageNum = 1;
        bookingFacade.getUsersByName(user.getName(), pageSize, pageNum);
        verify(mockUserService).getUsersByName(user.getName(), pageSize, pageNum);

        pageSize = 0;
        pageNum = 0;
        bookingFacade.getUsersByName(user.getName(), pageSize, pageNum);
        verify(mockUserService).getUsersByName(user.getName(), pageSize, pageNum);

        pageSize = 0;
        pageNum = 1;
        bookingFacade.getUsersByName(user.getName(), pageSize, pageNum);
        verify(mockUserService).getUsersByName(user.getName(), pageSize, pageNum);

        pageSize = 1;
        pageNum = 0;
        bookingFacade.getUsersByName(user.getName(), pageSize, pageNum);
        verify(mockUserService).getUsersByName(user.getName(), pageSize, pageNum);

        pageSize = -1;
        pageNum = -1;
        bookingFacade.getUsersByName(user.getName(), pageSize, pageNum);
        verify(mockUserService).getUsersByName(user.getName(), pageSize, pageNum);

        pageSize = 1;
        pageNum = -1;
        bookingFacade.getUsersByName(user.getName(), pageSize, pageNum);
        verify(mockUserService).getUsersByName(user.getName(), pageSize, pageNum);

        pageSize = -1;
        pageNum = 1;
        bookingFacade.getUsersByName(user.getName(), pageSize, pageNum);
        verify(mockUserService).getUsersByName(user.getName(), pageSize, pageNum);
    }

    @Test
    public void testCreateUser() {
        when(mockUserService.createUser(user)).thenReturn(user);
        User createdUser = bookingFacade.createUser(user);
        verify(mockUserService).createUser(user);

        assertNotNull(user);
        assertNotNull(createdUser);
        assertEquals(user, createdUser);
        assertEquals(createdUser.getName(), user.getName());
        assertEquals(createdUser.getEmail(), user.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserWithWrongParam() {
        bookingFacade.createUser(null);
        verify(mockUserService).createUser(null);
    }

    @Test
    public void testUpdateUser() {
        when(mockUserService.createUser(user)).thenReturn(user);
        User createdUser = bookingFacade.createUser(user);
        verify(mockUserService).createUser(user);

        assertNotNull(user);
        assertNotNull(createdUser);
        assertEquals(user, createdUser);

        String userName = "Joshua Bloch";
        String userEmail = "Joshua_Bloch@i.ua";
        User userUpd = new UserImpl(1L, userName, userEmail);

        when(bookingFacade.updateUser(userUpd)).thenReturn(userUpd);
        User receivedUser = bookingFacade.updateUser(userUpd);
        verify(mockUserService).updateUser(userUpd);

        assertNotNull(receivedUser);
        assertNotSame(createdUser, receivedUser);
        assertEquals(userUpd.getId(), receivedUser.getId());
        assertEquals(userUpd.getEmail(), receivedUser.getEmail());
        assertEquals(userUpd.getName(), receivedUser.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserWithWrongParam() {
        bookingFacade.updateUser(null);
        verify(mockUserService).updateUser(null);
    }

    @Test
    public void testDeleteUser() {
        when(mockUserService.deleteUser(1L)).thenReturn(Boolean.TRUE);
        assertEquals(bookingFacade.deleteUser(1L), true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserWithWrongParam() {
        assertTrue(bookingFacade.deleteUser(0L));
        verify(mockUserService).deleteUser(0L);

        assertTrue(bookingFacade.deleteUser(-1L));
        verify(mockUserService).deleteUser(0L);
    }

    //TICKETS
    @Test()
    public void testBookTicket() {
        when(mockTicketService.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD)).thenReturn(ticket);
        assertEquals(bookingFacade.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD), ticket);
        verify(mockTicketService).bookTicket(user.getId(), event.getId(), 1, Ticket.Category.STANDARD);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookTicketWithWrongParam() {
        long userId = 0;
        long eventId = 0;
        int place = 0;

        bookingFacade.bookTicket(userId, eventId, place, Ticket.Category.PREMIUM);
        verify(mockTicketService).bookTicket(userId, eventId, place, Ticket.Category.PREMIUM);

        userId = 0;
        eventId = 1;
        place = 1;
        bookingFacade.bookTicket(userId, eventId, place, Ticket.Category.PREMIUM);
        verify(mockTicketService).bookTicket(userId, eventId, place, Ticket.Category.PREMIUM);

        userId = 1;
        eventId = 0;
        place = 0;
        bookingFacade.bookTicket(userId, eventId, place, Ticket.Category.PREMIUM);
        verify(mockTicketService).bookTicket(userId, eventId, place, Ticket.Category.PREMIUM);

        bookingFacade.bookTicket(userId, eventId, place, null);
        verify(mockTicketService).bookTicket(userId, eventId, place, null);
    }

    @Test
    public void getBookedByUserTicket() {
        int pageSize = 1;
        int pageNum = 1;
        when(mockTicketService.getBookedTickets(user, pageSize, pageNum)).thenReturn(Arrays.asList(ticket));
        assertEquals(bookingFacade.getBookedTickets(user, 1, 1), Arrays.asList(ticket));
        verify(mockTicketService).getBookedTickets(user, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBookedByUserTicketWithWrongParam() {
        int pageSize = 0;
        int pageNum = 0;
        bookingFacade.getBookedTickets(user, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(user, pageSize, pageNum);

        pageSize = 0;
        pageNum = 1;
        bookingFacade.getBookedTickets(user, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(user, pageSize, pageNum);

        pageSize = 1;
        pageNum = 0;
        bookingFacade.getBookedTickets(user, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(user, pageSize, pageNum);

        pageSize = -1;
        pageNum = -1;
        bookingFacade.getBookedTickets(user, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(user, pageSize, pageNum);

        pageSize = 1;
        pageNum = -1;
        bookingFacade.getBookedTickets(user, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(user, pageSize, pageNum);

        pageSize = -1;
        pageNum = 1;
        bookingFacade.getBookedTickets(user, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(user, pageSize, pageNum);
    }

    @Test
    public void getBookedTicketsByEventTickets() {
        int pageSize = 1;
        int pageNum = 1;
        when(mockTicketService.getBookedTickets(event, pageSize, pageNum)).thenReturn(Arrays.asList(ticket));
        assertEquals(bookingFacade.getBookedTickets(event, 1, 1), Arrays.asList(ticket));
        verify(mockTicketService).getBookedTickets(event, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getBookedTicketsByEventTicketsWithWrongParam() {
        int pageSize = 0;
        int pageNum = 0;
        bookingFacade.getBookedTickets(event, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(event, pageSize, pageNum);

        pageSize = 0;
        pageNum = 1;
        bookingFacade.getBookedTickets(event, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(event, pageSize, pageNum);

        pageSize = 1;
        pageNum = 0;
        bookingFacade.getBookedTickets(event, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(event, pageSize, pageNum);

        pageSize = -1;
        pageNum = -1;
        bookingFacade.getBookedTickets(event, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(event, pageSize, pageNum);

        pageSize = 1;
        pageNum = -1;
        bookingFacade.getBookedTickets(event, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(event, pageSize, pageNum);

        pageSize = -1;
        pageNum = 1;
        bookingFacade.getBookedTickets(event, pageSize, pageNum);
        verify(mockTicketService).getBookedTickets(event, pageSize, pageNum);
    }

    @Test
    public void cancelTicket() {
        when(mockTicketService.cancelTicket(1L)).thenReturn(Boolean.TRUE);
        assertEquals(bookingFacade.cancelTicket(1L), true);
        verify(mockTicketService).cancelTicket(1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cancelTicketWithWrongParam() {
        when(mockTicketService.cancelTicket(0L)).thenReturn(Boolean.TRUE);
        assertTrue(bookingFacade.cancelTicket(0L));
        verify(mockTicketService).cancelTicket(0L);

        assertTrue(bookingFacade.cancelTicket(-1L));
        verify(mockTicketService).cancelTicket(-1L);
        verify(mockTicketService).cancelTicket(-1L);
    }

    //USER ACCOUNT
    @Test
    public void testCreateUserAccount() {
        when(mockAccountService.createUserAccount(userAccount)).thenReturn(userAccount);
        UserAccount createdUserAccount = bookingFacade.createUserAccount(userAccount);
        verify(mockAccountService).createUserAccount(userAccount);

        assertNotNull(userAccount);
        assertNotNull(createdUserAccount);
        assertEquals(userAccount, createdUserAccount);
        assertEquals(userAccount.getAmount(), createdUserAccount.getAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserAccountWithWrongParam() {
        bookingFacade.createUserAccount(null);
        verify(mockAccountService).createUserAccount(null);
    }

    @Test
    public void testGetUserAccountById() {
        when(mockAccountService.getUserAccountById(userAccount.getId())).thenReturn(userAccount);
        UserAccount receivedUserAccount = bookingFacade.getUserAccountById(userAccount.getId());
        verify(mockAccountService).getUserAccountById(userAccount.getId());

        assertEquals(userAccount.getUserId(), receivedUserAccount.getUserId());
        assertEquals(userAccount.getAmount(), receivedUserAccount.getAmount());
        assertEquals(userAccount, receivedUserAccount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserAccountByIdWithWrongParam() {
        bookingFacade.getUserAccountById(0L);
        verify(mockAccountService).getUserAccountById(0L);

        bookingFacade.getUserAccountById(-1L);
        verify(mockAccountService).getUserAccountById(-1L);
    }

    @Test
    public void testGetUserAccountByUserId() {
        when(mockAccountService.getUserAccountByUserId(userAccount.getUserId())).thenReturn(userAccount);
        UserAccount receivedUserAccount = bookingFacade.getUserAccountByUserId(userAccount.getUserId());
        verify(mockAccountService).getUserAccountByUserId(userAccount.getUserId());

        assertEquals(userAccount.getAmount(), receivedUserAccount.getAmount());
        assertEquals(userAccount.getId(), receivedUserAccount.getId());
        assertEquals(userAccount.getUserId(), receivedUserAccount.getUserId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserAccountByUserIdWithWrongParam() {
        bookingFacade.getUserAccountByUserId(0L);
        verify(mockAccountService).getUserAccountByUserId(0L);

        bookingFacade.getUserAccountByUserId(-1L);
        verify(mockAccountService).getUserAccountByUserId(-1L);
    }

    @Test
    public void testUpdateUserAccount() {
        BigDecimal updateBalance = new BigDecimal(3000);
        when(mockAccountService.getUserAccountById(userAccount.getId())).thenReturn(userAccount);
        UserAccount receivedUserAccount = bookingFacade.getUserAccountById(userAccount.getId());
        verify(mockAccountService).getUserAccountById(userAccount.getId());

        when(mockAccountService.updateUserAccount(receivedUserAccount)).thenReturn(receivedUserAccount);
        receivedUserAccount.setAmount(updateBalance);

        assertTrue(bookingFacade.getUserAccountById((userAccount.getId())).getAmount().compareTo(updateBalance) == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserAccountWithWrongParam() {
        bookingFacade.updateUserAccount(null);
        verify(mockAccountService).updateUserAccount(null);
    }

    @Test
    public void testDeleteUserAccount() {
        when(mockAccountService.deleteUserAccount(1L)).thenReturn(Boolean.TRUE);
        assertEquals(true, bookingFacade.deleteUserAccount(1L));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserAccountWithWrongParam() {
        bookingFacade.deleteUserAccount(-1L);
        verify(mockAccountService).deleteUserAccount(-1L);

        bookingFacade.deleteUserAccount(0L);
        verify(mockAccountService).deleteUserAccount(0L);
    }

    @Test
    public void testRechargeAccountByAccountId() {
        bookingFacade.rechargeAccountByAccountId(userAccount.getId(), BigDecimal.TEN);
        verify(mockAccountService).rechargeAccountByAccountId(userAccount.getId(), BigDecimal.TEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRechargeAccountByAccountIdWithWrongParam() {
        bookingFacade.rechargeAccountByAccountId(userAccount.getId(), BigDecimal.ZERO);
        verify(mockAccountService).rechargeAccountByAccountId(userAccount.getId(), BigDecimal.ZERO);

        bookingFacade.rechargeAccountByAccountId(0L, BigDecimal.ZERO);
        verify(mockAccountService).rechargeAccountByAccountId(0L, BigDecimal.ZERO);

        bookingFacade.rechargeAccountByAccountId(0L, BigDecimal.TEN);
        verify(mockAccountService).rechargeAccountByAccountId(0L, BigDecimal.TEN);

        bookingFacade.rechargeAccountByAccountId(-1L, BigDecimal.ONE);
        verify(mockAccountService).rechargeAccountByAccountId(-1L, BigDecimal.ONE);
    }

    @Test
    public void testRechargeAccountByUserId() {
        bookingFacade.rechargeAccountByUserId(userAccount.getUserId(), BigDecimal.TEN);
        verify(mockAccountService).rechargeAccountByUserId(userAccount.getUserId(), BigDecimal.TEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRechargeAccountByUserIdWithWrongParam() {
        bookingFacade.rechargeAccountByUserId(userAccount.getUserId(), BigDecimal.ZERO);
        verify(mockAccountService).rechargeAccountByUserId(userAccount.getUserId(), BigDecimal.ZERO);

        bookingFacade.rechargeAccountByUserId(0L, BigDecimal.ZERO);
        verify(mockAccountService).rechargeAccountByUserId(0L, BigDecimal.ZERO);

        bookingFacade.rechargeAccountByUserId(0L, BigDecimal.TEN);
        verify(mockAccountService).rechargeAccountByUserId(0L, BigDecimal.TEN);

        bookingFacade.rechargeAccountByUserId(-1L, BigDecimal.TEN);
        verify(mockAccountService).rechargeAccountByUserId(-1L, BigDecimal.TEN);

    }
}