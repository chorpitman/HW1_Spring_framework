package com.epam.service;

import com.epam.dao.EventDao;
import com.epam.dao.TicketDao;
import com.epam.dao.UserAccountDao;
import com.epam.dao.UserDao;
import com.epam.model.Ticket;
import com.epam.model.impl.EventImpl;
import com.epam.model.impl.TicketImpl;
import com.epam.model.impl.UserAccountImpl;
import com.epam.model.impl.UserImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@Configuration
//@ImportResource("classpath:app-context.xml")
@Profile("test")
@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {
    @InjectMocks
    UserImpl user;
    @InjectMocks
    EventImpl event;
    @InjectMocks
    UserAccountImpl userAccount;
    @InjectMocks
    TicketImpl ticket;

    @Mock
    private TicketDao mockTicketDao;
    @Mock
    private UserDao userDao;
    @Mock
    private EventDao eventDao;
    @Mock
    private UserAccountDao accountDao;
    @Mock
    private TransactionTemplate transactionTemplate;

    @Before
    public void init() {
        user = new UserImpl(1L, "Dexter", "d@i.ua");
        event = new EventImpl(1L, "Opera", new Date(), BigDecimal.ONE);
        userAccount = new UserAccountImpl(1L, 1L, BigDecimal.ZERO);
        ticket = new TicketImpl(1L, Ticket.Category.PREMIUM, 1L, 1L, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookTicketException() {
        //not enough money on balance
        when(mockTicketDao.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM)).thenThrow(new IllegalArgumentException());
        mockTicketDao.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM);
        verify(mockTicketDao).bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM);
    }

    @Test
    public void testBookTicket() {
        when(mockTicketDao.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM)).thenReturn(ticket);
        Ticket bookTicket = mockTicketDao.bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM);
        verify(mockTicketDao).bookTicket(user.getId(), event.getId(), 1, Ticket.Category.PREMIUM);
        assertNotNull(bookTicket);
    }

    @Test
    public void testGetBookedByUserTickets() {
        int pageSize = 1;
        int pageNum = 1;

        when(mockTicketDao.getBookedTickets(user, pageSize, pageNum)).thenReturn(Arrays.asList(ticket));
        assertEquals(mockTicketDao.getBookedTickets(user, 1, 1), Arrays.asList(ticket));
        verify(mockTicketDao).getBookedTickets(user, 1, 1);
    }

    @Test
    public void testGetBookedByEventTickets() {
        int pageSize = 1;
        int pageNum = 1;

        when(mockTicketDao.getBookedTickets(event, pageSize, pageNum)).thenReturn(Arrays.asList(ticket));
        assertEquals(mockTicketDao.getBookedTickets(event, 1, 1), Arrays.asList(ticket));
        verify(mockTicketDao).getBookedTickets(event, 1, 1);
    }

    @Test
    public void testCancelTicket() {
        long ticketId = 1L;
        when(mockTicketDao.cancelTicket(ticketId)).thenReturn(Boolean.TRUE);
        assertEquals(true, mockTicketDao.cancelTicket(ticketId));
        assertEquals(false, mockTicketDao.cancelTicket(0));
        assertEquals(false, mockTicketDao.cancelTicket(-1));
        assertEquals(false, mockTicketDao.cancelTicket(100));
        verify(mockTicketDao).cancelTicket(ticketId);
    }
}