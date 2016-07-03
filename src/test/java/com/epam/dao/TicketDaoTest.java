package com.epam.dao;

import com.epam.config.ServiceTestConfig;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.impl.EventImpl;
import com.epam.model.impl.TicketImpl;
import com.epam.model.impl.UserImpl;
import com.epam.utils.TicketException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class TicketDaoTest {
    private Ticket ticket;
    private User user;
    private Event event;

    @Autowired
    TicketDao ticketDao;

    @Autowired
    UserDao userDao;

    @Autowired
    EventDao eventDao;

    @Before
    public void init() {
        ticket = new TicketImpl(1L, Ticket.Category.STANDARD, 4, 7, 1);
    }

    @Test
    public void testBookedTicketById() {
        assertNotNull(ticketDao.bookedTicketById(1L));
    }

    @Test(expected = TicketException.class)
    public void testBookedTicketByIdWrongId() {
        assertNotNull(ticketDao.bookedTicketById(-1L));
        assertNotNull(ticketDao.bookedTicketById(0L));
        assertNotNull(ticketDao.bookedTicketById(100L));
    }


    @Test
    public void testBookTicket() {
        Ticket bookTicket = ticketDao.bookTicket(ticket.getId(), ticket.getEventId(), ticket.getPlace(), ticket.getCategory());
        assertNotNull(bookTicket);
        assertEquals(ticket.getCategory(), bookTicket.getCategory());
        assertEquals(ticket.getEventId(), bookTicket.getEventId());
        assertEquals(ticket.getPlace(), bookTicket.getPlace());
    }

    @Test
    public void testGetBookedTicketsByUser() {
        user = userDao.getUserById(1);
        List<Ticket> receivedTicket = ticketDao.getBookedTickets(user, 2, 1);
        assertEquals(2, receivedTicket.size());

        receivedTicket = ticketDao.getBookedTickets(user, 1, 1);
        assertEquals(1, receivedTicket.size());

        receivedTicket = ticketDao.getBookedTickets(user, 1, 2);
        assertEquals(1, receivedTicket.size());
    }

    @Test(expected = TicketException.class)
    public void testGetBookedTicketsByUserWrongUser() {
        user = new UserImpl(100L, "Alex", "Alex@i.ua");
        assertNotNull(user);
        ticketDao.getBookedTickets(user, 2, 1);

        user = userDao.getUserById(1L);
        assertNotNull(user);

        List<Ticket> receivedTicket = ticketDao.getBookedTickets(user, 0, 0);
        assertNotNull(receivedTicket);
        assertEquals(0, receivedTicket.size());

        receivedTicket = ticketDao.getBookedTickets(user, -1, -1);
        assertEquals(0, receivedTicket.size());
    }

    @Test
    public void testGetBookedTicketsByEvent() {
        event = eventDao.getEventById(1L);
        List<Ticket> receivedTicket = ticketDao.getBookedTickets(event, 2, 1);
        assertEquals(2, receivedTicket.size());

        receivedTicket = ticketDao.getBookedTickets(event, 1, 1);
        assertEquals(1, receivedTicket.size());

        receivedTicket = ticketDao.getBookedTickets(event, 1, 2);
        assertEquals(1, receivedTicket.size());
    }

    @Test(expected = TicketException.class)
    public void testGetBookedTicketsByEventWrongEvent() {
        event = new EventImpl(100L, "Moulin Rouge", new Date(), BigDecimal.ZERO);
        assertNotNull(event);
        ticketDao.getBookedTickets(event, 1, 1);

        event = eventDao.getEventById(1L);
        List<Ticket> receivedTicket = ticketDao.getBookedTickets(event, 0, 0);
        assertNotNull(receivedTicket);
        assertEquals(0, receivedTicket.size());

        receivedTicket = ticketDao.getBookedTickets(event, -1, -1);
        assertEquals(0, receivedTicket.size());
    }

    @Test
    public void testCancelTicket() {
        assertEquals(true, ticketDao.cancelTicket(1L));
    }

    @Test(expected = TicketException.class)
    public void testCancelTicketWrongParam() {
        assertEquals(false, ticketDao.cancelTicket(-1L));
        assertEquals(false, ticketDao.cancelTicket(0L));
        assertEquals(false, ticketDao.cancelTicket(-100L));
    }

    @Test(expected = TicketException.class)
    public void testCancelTickettesNotExistTicket() {
        assertEquals(true, ticketDao.cancelTicket(1L));
        assertEquals(false, ticketDao.cancelTicket(1L));
    }
}