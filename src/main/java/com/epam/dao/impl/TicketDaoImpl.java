package com.epam.dao.impl;

import com.epam.dao.TicketDao;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.storage.EntityStorage;

import java.util.List;

public class TicketDaoImpl implements TicketDao {

    private EntityStorage storage;

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return storage.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return storage.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return storage.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return storage.cancelTicket(ticketId);
    }
}
