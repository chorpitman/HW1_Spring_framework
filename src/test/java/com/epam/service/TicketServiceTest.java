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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private TransactionTemplate transactionTemplate;


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
    public void testTxBookTicketException() {
        TransactionStatus transactionStatus = transactionTemplate.getTransactionManager().getTransaction(transactionTemplate);
        userAccountService.rechargeAccountByUserId(2, new BigDecimal(100));
        try {
            Ticket bookedTicket = ticketService.bookTicket(2, 2, 20, Ticket.Category.BAR);
        } catch (Exception e) {
            assertTrue(transactionStatus.isRollbackOnly());
        }
        transactionTemplate.getTransactionManager().rollback(transactionStatus);
    }

    @Test
    public void testTxBookTicket() {
        TransactionStatus transactionStatus = transactionTemplate.getTransactionManager().getTransaction(transactionTemplate);
        userAccountService.rechargeAccountByUserId(2, new BigDecimal(301));
        try {
            Ticket bookedTicket = ticketService.bookTicket(2, 2, 20, Ticket.Category.BAR);
        } catch (Exception e) {
            assertFalse(transactionStatus.isRollbackOnly());
        } finally {
            transactionTemplate.getTransactionManager().rollback(transactionStatus);
        }
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
}