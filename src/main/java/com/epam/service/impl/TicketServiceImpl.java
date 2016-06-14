package com.epam.service.impl;

import com.epam.dao.EventDao;
import com.epam.dao.TicketDao;
import com.epam.dao.UserAccountDao;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.UserAccount;
import com.epam.service.TicketService;

import java.util.Collections;
import java.util.List;

public class TicketServiceImpl implements TicketService {
    private TicketDao ticketDao;
    private UserAccountDao userAccountDao;
    private EventDao eventDao;

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        if (userId <= 0 || eventId <= 0 || place <= 0 || category == null) {
            return null;
        }

        //check for existing ticket todo think about this methods and about approach which can test Ticketservic --> bookTicket
//        if (ticketDao.bookTicket(userId, eventId, place, category) != null) {
//            throw new IllegalArgumentException("ticket already booked");
//        }

        //check userAccountDao for money
        UserAccount userAccount = userAccountDao.getUserAccountByUserId(userId);
        System.out.println(userAccount);

        Event event = eventDao.getEventById(eventId);

        System.out.println("compare check -->>" + userAccount.getAmount().compareTo(event.getTicketPrice()));

        if (userAccount.getAmount().compareTo(event.getTicketPrice()) == -1) {

            throw new IllegalArgumentException("User don't have enough money on his balance");
        }

        //withdraw money from, user account
        userAccount.setAmount(userAccount.getAmount().subtract(event.getTicketPrice()));
        System.out.println("BALANCE AFTER BUYING TICKET -->>>" + userAccount.getAmount());
        userAccountDao.updateUserAccount(userAccount);

        return ticketDao.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        if (pageSize <= 0 || pageNum <= 0) {
            return Collections.emptyList();
        }
        return ticketDao.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        if (pageSize <= 0 || pageNum <= 0) {
            return Collections.emptyList();
        }
        return ticketDao.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return ticketDao.cancelTicket(ticketId);
    }

    @Override
    public Ticket bookedTicketById(long ticketId) {
        return ticketDao.bookedTicketById(ticketId);
    }
}
