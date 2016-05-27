package com.epam.storage;

import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.impl.TicketImpl;
import com.epam.utils.CollectionUtils;
import com.epam.utils.Pagination;
import com.epam.utils.IPredicate;

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
        List<User> userList = CollectionUtils.filter(storage.values(), name, User.class, new IPredicate<User, String>() {
            @Override
            public boolean apply(User type, String value) {
                return type.getName().equals(value);
            }
        });
        return Pagination.getData(userList, pageSize, pageNum);
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
        List<Event> eventList = CollectionUtils.filter(storage.values(), title, Event.class, new IPredicate<Event, String>() {
            @Override
            public boolean apply(Event type, String value) {
                return type.getTitle().equals(value);
            }
        });
        return Pagination.getData(eventList, pageSize, pageNum);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        List<Event> eventList = CollectionUtils.filter(storage.values(), day, Event.class, new IPredicate<Event, Date>() {
            @Override
            public boolean apply(Event type, Date value) {
                return type.getDate().equals(value);
            }
        });
        return Pagination.getData(eventList, pageSize, pageNum);
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
        List<Ticket> ticketList = CollectionUtils.filter(storage.values(), user.getId(), Ticket.class, new IPredicate<Ticket, Long>() {
            @Override
            public boolean apply(Ticket type, Long value) {
                return type.getUserId() == value;
            }
        });
        return Pagination.getData(ticketList, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> ticketList = CollectionUtils.filter(storage.values(), event.getId(), Ticket.class, (type, value) -> type.getEventId() == value);

        return Pagination.getData(ticketList, pageSize, pageNum);
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