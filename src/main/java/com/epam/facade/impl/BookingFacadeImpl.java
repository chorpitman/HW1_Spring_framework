package com.epam.facade.impl;

import com.epam.facade.BookingFacade;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.service.UserService;

import java.util.Date;
import java.util.List;

public class BookingFacadeImpl implements BookingFacade {

    private UserService userService;

    //constructor
    public BookingFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    // START EVENT
    public Event getEventById(long eventId) {
        return null;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return null;
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return null;
    }

    public Event createEvent(Event event) {
        return null;
    }

    public Event updateEvent(Event event) {
        return null;
    }

    public boolean deleteEvent(long eventId) {
        return false;
    }

    // START USER
    public User getUserById(long userId) {
        return userService.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        return null;
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return null;
    }

    public User createUser(User user) {
        return userService.createUser(user);
    }

    public User updateUser(User user) {
        return null;
    }

    public boolean deleteUser(long userId) {
        return false;
    }

    // START Ticket
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return null;
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return null;
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return null;
    }

    public boolean cancelTicket(long ticketId) {
        return false;
    }
}
