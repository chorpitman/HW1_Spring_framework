package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.UserAccount;
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

    private User user;
    private Event event;
    private Ticket ticket;
    private UserAccount userAccount;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserAccountService userAccountService;

//    @Before
//    public void init() {
//        user = new UserImpl("Travolta", "travolta@gmail.com");
//        userService.createUser(user);
//
//        event = new EventImpl("baseball", new Date(), new BigDecimal("100"));
//        eventService.createEvent(event);
//
//        ticket = new TicketImpl(Ticket.Category.PREMIUM, event.getId(), user.getId(), 666);
//    }


    @Test(expected = IllegalArgumentException.class)
    public void testBookTicketException() {
        //not enough money on balance
        ticketService.bookTicket(1, 1, 10, Ticket.Category.PREMIUM);
    }

    @Test()
    public void testBookTicket() {
        userAccountService.rechargeAccount(2, new BigDecimal(201));
        Ticket bokedTicket = ticketService.bookTicket(2, 2, 20, Ticket.Category.BAR);
        assertNotNull(bokedTicket);

        Event receivedEvent = eventService.getEventById(2);
    }

    @Test
    public void testGetBookedByUserTickets() {
        Ticket bookedTicket = ticketService.bookTicket(user.getId(), event.getId(), ticket.getPlace(), ticket.getCategory());
        List<Ticket> bookedTickets = ticketService.getBookedTickets(user, 1, 1);

        assertEquals(Arrays.asList(bookedTicket), bookedTickets);
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 0, 0));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 1, 0));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 0, 1));
    }

    @Test
    public void testGetBookedByEventTickets() {
        Ticket bookedTicket = ticketService.bookTicket(user.getId(), event.getId(), ticket.getPlace(), ticket.getCategory());
        List<Ticket> bookedTickets = ticketService.getBookedTickets(event, 1, 1);

        assertEquals(Arrays.asList(bookedTicket), bookedTickets);
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 0, 0));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 1, 0));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 1, 0));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(event, 0, 1));
    }

    @Test
    public void testCancelTicket() {
        long ticketID = ticket.getId();
        ticketService.bookTicket(user.getId(), event.getId(), ticket.getPlace(), ticket.getCategory());
        ticketService.cancelTicket(ticketID);
    }
}