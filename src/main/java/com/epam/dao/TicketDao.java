package com.epam.dao;

import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;

import java.util.List;

public interface TicketDao {

    Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category);

    List<Ticket> getBookedTickets(User user, int pageSize, int pageNum);

    List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum);

    boolean cancelTicket(long ticketId);

    Ticket bookedTicketById(long ticketId);
}