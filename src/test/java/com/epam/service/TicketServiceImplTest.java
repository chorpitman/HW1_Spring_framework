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

import java.util.Date;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
public class TicketServiceImplTest {

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

        long id = bokedTicket.getId();
        long evId = bokedTicket.getEventId();
        long uID = bokedTicket.getUserId();
        Ticket.Category val = bokedTicket.getCategory();
        assertEquals(1, evId);
    }

    @Test
    public void testGetBookedByUserTickets() throws Exception {
        ticketService.bookTicket(user.getId(), event.getId(), ticket.getPlace(), ticket.getCategory());
//        List<Ticket> bookedTickets = ticketService.getBookedTickets(user, 1, 1);
    }

    @Test
    public void testGetBookedByEventTickets() throws Exception {

    }

    @Test
    public void testCancelTicket() throws Exception {
//        long ticketID = ticket.getId();
//        ticketService.bookTicket(user.getId(), event.getId(), ticket.getPlace(),ticket.getCategory());
//        ticketService.cancelTicket(ticketID);
//        assertEquals(ticket.getId(), null);
    }
}