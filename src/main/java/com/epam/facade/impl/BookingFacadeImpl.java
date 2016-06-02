package com.epam.facade.impl;

import com.epam.facade.BookingFacade;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.service.EventService;
import com.epam.service.TicketService;
import com.epam.service.UserService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BookingFacadeImpl implements BookingFacade {

    private UserService userService;
    private EventService eventService;
    private TicketService ticketService;

    //constructor
    public BookingFacadeImpl(UserService userService, EventService eventService, TicketService ticketService) {
        this.userService = userService;
        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    // START EVENT
    public Event getEventById(long eventId) {
        if (eventId <= 0) {
            return null;
        }
        return eventService.getEventById(eventId);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    public Event createEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("wrong param");
        }
        return eventService.createEvent(event);
    }

    public Event updateEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("wrong param");
        }
        return eventService.updateEvent(event);
    }

    public boolean deleteEvent(long eventId) {
        if (eventId <= 0) {
            return false;
        }
        return eventService.deleteEvent(eventId);
    }

    // START USER
    public User getUserById(long userId) {
        if (userId <= 0) {
            return null;
        }
        return userService.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("wrong param");
        }
        return userService.createUser(user);
    }

    public User updateUser(User user) {
        if (user == null) {
            return null;
        }
        return userService.updateUser(user);
    }

    public boolean deleteUser(long userId) {
        if (userId <= 0) {
            return false;
        }
        return userService.deleteUser(userId);
    }

    // START Ticket
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        if (userId <= 0 || eventId <= 0 || place <= 0 || category == null) {
            throw new IllegalArgumentException("wrong param");
        }
        return ticketService.bookTicket(userId, eventId, place, category);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    public boolean cancelTicket(long ticketId) {
        if (ticketId <= 0) {
            return false;
        }
        return ticketService.cancelTicket(ticketId);
    }

    @Override
    public void rechargeAccount(long userId, BigDecimal amount) {

    }
}
