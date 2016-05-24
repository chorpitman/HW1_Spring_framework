package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.impl.EventImpl;
import com.epam.model.impl.TicketImpl;
import com.epam.model.impl.UserImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
public class TicketServiceTest {

    private User user;
    private Event event;
    private Ticket ticket;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Before
    public void init() throws Exception {
        user = new UserImpl("Joe", "Joe@gmail.com");
        userService.createUser(user);

        event = new EventImpl("baseball", new Date());
        eventService.createEvent(event);

        ticket = new TicketImpl(Ticket.Category.PREMIUM, event.getId(), user.getId(), 666);
    }

    @After
    public void cleanStorage() throws Exception {
        ticketService.cancelTicket(ticket.getId());
    }

    @Test
    public void testBookTicket() throws Exception {
        Ticket bokedTicket = ticketService.bookTicket(user.getId(), event.getId(), ticket.getPlace(), ticket.getCategory());

        long bokedEventId = bokedTicket.getEventId();
        long bokedUserId = bokedTicket.getUserId();
        Ticket.Category bokedTicketCategory = bokedTicket.getCategory();
        long bokedPlace = bokedTicket.getPlace();

        assertEquals(event.getId(), bokedEventId);
        assertEquals(user.getId(), bokedUserId);
        assertEquals(Ticket.Category.PREMIUM, bokedTicketCategory);
        assertEquals(ticket.getPlace(), bokedPlace);


    }

    @Test
    public void testGetBookedByUserTickets() throws Exception {
        Ticket bookedTicket = ticketService.bookTicket(user.getId(), event.getId(), ticket.getPlace(), ticket.getCategory());
        List<Ticket> bookedTickets = ticketService.getBookedTickets(user, 1, 1);

        assertEquals(Arrays.asList(bookedTicket), bookedTickets);
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 0, 0 ));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 1, 0 ));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 0, 1 ));
    }

    @Test
    public void testGetBookedByEventTickets() throws Exception {
        Ticket bookedTicket = ticketService.bookTicket(user.getId(), event.getId(), ticket.getPlace(), ticket.getCategory());
        List<Ticket> bookedTickets = ticketService.getBookedTickets(event, 1, 1);

        assertEquals(Arrays.asList(bookedTicket), bookedTickets);
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 0, 0 ));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 1, 0 ));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(user, 1, 0 ));
        assertEquals(Collections.emptyList(), ticketService.getBookedTickets(event, 0, 1 ));
    }

    @Test
    public void testCancelTicket() throws Exception {
        long ticketID = ticket.getId();
        ticketService.bookTicket(user.getId(), event.getId(), ticket.getPlace(),ticket.getCategory());
        ticketService.cancelTicket(ticketID);
    }
}