package com.epam.storage;

import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.impl.TicketImpl;
import com.epam.utils.Pagination;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Like DB
public class EntityStorage {
    private static final String USER_NS = "user:";
    private static final String EVENT_NS = "event:";
    private static final String TICKET_NS = "ticket:";

    //DB
    private Map<String, Object> storage = new HashMap<>();

    //USER
    public User createUser(User user) {
        if (user != null) {
            storage.put(USER_NS + user.getId(), user);
            return (User) storage.get(USER_NS + user.getId());
        }
        throw new IllegalArgumentException("wrong param");
    }

    public User update(User user) {
        if (user != null) {
            storage.put(USER_NS + user.getId(), user);
            return (User) storage.get(USER_NS + user.getId());
        }
        return null;
    }

    public boolean deleteUser(long userId) {
        if (userId > 0) {
            for (String user : storage.keySet()) {
                if (user.equals(USER_NS + userId)) {
                    storage.remove(user);
                    return true;
                }
            }
        }
        return false;
    }

    public User getUserById(long id) {
        if (id > 0) {
            return (User) storage.get(USER_NS + id);
        }
        return null;
    }

    public User getUserByEmail(String email) {
        for (Object user : storage.values()) {
            if (((User) user).getEmail().equals(email)) {
                return (User) user;
            }
        }
        return null;
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return Pagination.usersPagination(storage, name, pageSize, pageNum);
    }


    //EVENT
    public Event createEvent(Event event) {
        if (event != null) {
            storage.put(EVENT_NS + event.getId(), event);
            return (Event) storage.get(EVENT_NS + event.getId());
        }
        return null;
    }

    public Event updateEvent(Event event) {
        if (event != null) {
            return (Event) storage.put(USER_NS + event.getId(), event);
        }
        return null;
    }

    public boolean deleteEvent(long eventId) {
        if (eventId > 0) {
            for (String event : storage.keySet()) {
                if (event.equals(EVENT_NS + eventId)) {
                    storage.remove(event);
                    return true;
                }
            }
        }
        return false;
    }

    public Event getEventById(long id) {
        if (id > 0) {
            return (Event) storage.get(EVENT_NS + id);
        }
        return null;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return Pagination.eventsPaginationByTitle(storage, title, pageSize, pageNum);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return Pagination.eventsPaginationForDay(storage, day, pageSize, pageNum);
    }

    //TICKETS
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        if (userId <= 0 || eventId <= 0 || place <= 0 || category == null) {
            throw new IllegalArgumentException("wrong param");
        }

        Ticket ticket = new TicketImpl();
        ticket.setUserId(userId);
        ticket.setEventId(eventId);
        ticket.setPlace(place);
        ticket.setCategory(category);
        storage.put(TICKET_NS + ticket.getId(), ticket);
        return ticket;
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return Pagination.bookTicketByUserPagination(storage, user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return Pagination.bookTicketByEventPagination(storage, event, pageSize, pageNum);
    }

    public boolean cancelTicket(long ticketId) {
        if (ticketId > 0) {
            for (String ticket : storage.keySet()) {
                if (ticket.equals(EVENT_NS + ticketId)) {
                    storage.remove(ticket);
                    return true;
                }
            }
        }
        return false;
    }
}