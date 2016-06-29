package com.epam.facade.impl;

import com.epam.facade.BookingFacade;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.UserAccount;
import com.epam.service.EventService;
import com.epam.service.TicketService;
import com.epam.service.UserAccountService;
import com.epam.service.UserService;
import com.epam.utils.Validator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BookingFacadeImpl implements BookingFacade {

    private UserService userService;
    private EventService eventService;
    private TicketService ticketService;
    private UserAccountService userAccountService;

    //constructor
    public BookingFacadeImpl(UserService userService, EventService eventService, TicketService ticketService, UserAccountService userAccountService) {
        this.userService = userService;
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.userAccountService = userAccountService;
    }

    // START EVENT
    public Event getEventById(long eventId) {
        Validator.checkExpression(eventId <= 0, " eventID must be more than 0");
        return eventService.getEventById(eventId);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        Validator.checkExpression(title == null || pageNum <= 0 || pageNum <= 0, "title can't be null or pageSize " +
                "and pageNum must be more than 0");
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        Validator.checkExpression(day == null || pageSize <= 0 || pageNum <= 0, "day can't be null or pageSize " +
                "and pageNum must be more than 0");
        return eventService.getEventsForDay(day, pageSize, pageNum);
    }

    public Event createEvent(Event event) {
        Validator.checkNotNull(event);
        return eventService.createEvent(event);
    }

    public Event updateEvent(Event event) {
        Validator.checkNotNull(event);
        return eventService.updateEvent(event);
    }

    public boolean deleteEvent(long eventId) {
        Validator.checkExpression(eventId <= 0, " eventId must be more than 0");
        return eventService.deleteEvent(eventId);
    }

    // START USER
    public User getUserById(long userId) {
        Validator.checkExpression(userId <= 0, " userId must be more than 0");
        return userService.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        Validator.checkNotNull(email);
        return userService.getUserByEmail(email);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        Validator.checkExpression(name == null || pageSize <= 0 || pageNum <= 0, "name can't be null or pageSize " +
                "and pageNum must be more than 0");
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    public User createUser(User user) {
        Validator.checkNotNull(user);
        return userService.createUser(user);
    }

    public User updateUser(User user) {
        Validator.checkNotNull(user);
        return userService.updateUser(user);
    }

    public boolean deleteUser(long userId) {
        Validator.checkExpression(userId <= 0, " userId must be more than 0");
        return userService.deleteUser(userId);
    }

    // START Ticket
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        Validator.checkExpression(userId <= 0 || eventId <= 0 || place <= 0 || category == null, "place or category " +
                "can't be null or pageSize, and pageNum must be more than 0");
        return ticketService.bookTicket(userId, eventId, place, category);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        Validator.checkExpression(user == null || pageSize <= 0 || pageNum <= 0, "user can't be null or pageSize " +
                "and pageNum must be more than 0");
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        Validator.checkExpression(event == null || pageSize <= 0 || pageNum <= 0, "event can't be null or pageSize " +
                "and pageNum must be more than 0");
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    public boolean cancelTicket(long ticketId) {
        Validator.checkExpression(ticketId <= 0, "ticketId must be more than 0");
        return ticketService.cancelTicket(ticketId);
    }

    //USER ACCOUNT
    @Override
    public UserAccount createUserAccount(UserAccount account) {
        Validator.checkNotNull(account);
        return userAccountService.createUserAccount(account);
    }

    @Override
    public UserAccount getUserAccountById(long accountId) {
        Validator.checkExpression(accountId <= 0, "AccountId must be more than 0");
        return userAccountService.getUserAccountById(accountId);
    }

    @Override
    public UserAccount getUserAccountByUserId(long userAccountId) {
        Validator.checkExpression(userAccountId <= 0, "AccountId must be more than 0");
        return userAccountService.getUserAccountByUserId(userAccountId);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount account) {
        Validator.checkNotNull(account);
        return userAccountService.updateUserAccount(account);
    }

    @Override
    public boolean deleteUserAccount(long accountId) {
        Validator.checkExpression(accountId <= 0, "accountId must be more than 0");
        return userAccountService.deleteUserAccount(accountId);
    }

    @Override
    public void rechargeAccountByAccountId(long accountId, BigDecimal amount) {
        Validator.checkExpression(accountId <= 0 || amount.compareTo(new BigDecimal(0)) <= 0, "accountId and amount " +
                "must be more than 0");
        userAccountService.rechargeAccountByAccountId(accountId, amount);
    }

    @Override
    public void rechargeAccountByUserId(long userId, BigDecimal amount) {
        Validator.checkExpression(userId <= 0 || amount.compareTo(new BigDecimal(0)) <= 0, "userId and amount " +
                "must be more than 0");
        userAccountService.rechargeAccountByUserId(userId, amount);
    }
}
