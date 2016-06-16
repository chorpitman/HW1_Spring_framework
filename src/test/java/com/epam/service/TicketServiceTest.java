package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:drop.sql", "classpath:ddl_InMem.sql", "classpath:dml_InMem.sql"})
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserAccountService userAccountService;

    @Test(expected = IllegalArgumentException.class)
    public void testBookTicketException() {
        //not enough money on balance
        ticketService.bookTicket(1, 1, 10, Ticket.Category.PREMIUM);
    }

    @Test
    public void testBookTicket() {
        userAccountService.rechargeAccountByUserId(2, new BigDecimal(201));
        Ticket bookedTicket = ticketService.bookTicket(2, 2, 20, Ticket.Category.BAR);
        assertNotNull(bookedTicket);

        Event receivedEvent = eventService.getEventById(2);
        assertEquals(new BigDecimal(201), receivedEvent.getTicketPrice());
    }

    @Test
    public void testGetBookedByUserTickets() {
        User receivedUser = userService.getUserById(1);
        List<Ticket> bookedTickets = ticketService.getBookedTickets(receivedUser, 1, 1);
        assertEquals(Arrays.asList(ticketService.bookedTicketById(1)), bookedTickets);

        List<Ticket> bookedTickets1 = ticketService.getBookedTickets(receivedUser, 1, 2);
        assertEquals(Arrays.asList(ticketService.bookedTicketById(4)), bookedTickets1);

        List<Ticket> bookedTickets2 = ticketService.getBookedTickets(receivedUser, 2, 1);
        assertEquals(Arrays.asList(ticketService.bookedTicketById(1), ticketService.bookedTicketById(4)), bookedTickets2);
    }

    @Test
    public void testGetBookedByUserTicketsWithWrongPagination() {
        User receivedUser = userService.getUserById(1);
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(receivedUser, 0, 0));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(receivedUser, 1, 0));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(receivedUser, 0, 1));

        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(receivedUser, -1, 1));
    }

    @Test
    public void testGetBookedByEventTickets() {
        Event bookedEvent = eventService.getEventById(1);

        List<Ticket> bookedTickets = ticketService.getBookedTickets(bookedEvent, 2, 1);
        assertEquals(bookedTickets, Arrays.asList(ticketService.bookedTicketById(1), ticketService.bookedTicketById(2)));

        List<Ticket> bookedTickets1 = ticketService.getBookedTickets(bookedEvent, 1, 1);
        assertEquals(Arrays.asList(ticketService.bookedTicketById(1)), bookedTickets1);

        List<Ticket> bookedTickets2 = ticketService.getBookedTickets(bookedEvent, 1, 2);
        assertEquals(Arrays.asList(ticketService.bookedTicketById(2)), bookedTickets2);
    }

    @Test
    public void testCancelTicket() {
        assertEquals(true, ticketService.cancelTicket(1));
        assertEquals(true, ticketService.cancelTicket(2));
        assertEquals(true, ticketService.cancelTicket(3));
    }

    @Test
    public void testCancelTicketWithWrongId() {
        assertEquals(false, ticketService.cancelTicket(100));
        assertEquals(false, ticketService.cancelTicket(0));
        assertEquals(false, ticketService.cancelTicket(-1));
    }

    @Test
    public void testReturnExceptionWhenBookTicket() {
//        User user = new UserImpl(1L, "1", "1");
//        user = bookingFacade.createUser(user);
//        bookingFacade.refillUserAccount(user, new BigDecimal(1));
//        Event event = new EventImpl(1L, "1", new Date(), new BigDecimal(10));
//        event = bookingFacade.createEvent(event);
//
//        TransactionStatus transaction = transactionTemplate.getTransactionManager().getTransaction(transactionTemplate);
//        try {
//            bookingFacade.bookTicket(user.getId(), event.getId(), 10, Ticket.Category.STANDARD);
//        } catch (Exception e) {
//            assertTrue("", transaction.isRollbackOnly());
//        }
//        transactionTemplate.getTransactionManager().rollback(transaction);
    }

    @Test
    public void testReturnTicketWhenBookTicket() {
//        User user = new UserImpl(1L, "2", "1");
//        user = bookingFacade.createUser(user);
//        bookingFacade.refillUserAccount(user, new BigDecimal(100));
//        Event event = new EventImpl(1L, "1", new Date(), new BigDecimal(10));
//        event = bookingFacade.createEvent(event);
//
//        TransactionStatus transaction = transactionTemplate.getTransactionManager().getTransaction(transactionTemplate);
//        try {
//            bookingFacade.bookTicket(user.getId(), event.getId(), 10, Ticket.Category.STANDARD);
//        } finally {
//            assertFalse("", transaction.isRollbackOnly());
//            transactionTemplate.getTransactionManager().rollback(transaction);
//        }
    }
}