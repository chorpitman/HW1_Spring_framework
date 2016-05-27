package com.epam.storage;

import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.impl.TicketImpl;
import com.epam.utils.CollectionUtils;
import com.epam.utils.IPredicate;
import com.epam.utils.Pagination;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Like DB
public class EntityStorage {
    //DB
    private Map<String, Object> storage = new HashMap<>();

    //Prefix
    private <T> String getIdPrefix(T t) {
        return t.getClass().getInterfaces()[0].getSimpleName().toLowerCase() + ":";
    }

    private <T> String getIdPrefix(Class<T> clazz) {
        return clazz.getSimpleName().toLowerCase() + ":";
    }

    //USER
    public User createUser(User user) {
        if (user != null) {
            storage.put(getIdPrefix(user) + user.getId(), user);
            return (User) storage.get(getIdPrefix(user) + user.getId());
        }
        throw new IllegalArgumentException("wrong param");
    }

    public User update(User user) {
        if (user != null) {
            storage.put(getIdPrefix(user) + user.getId(), user);
            return (User) storage.get(getIdPrefix(user) + user.getId());
        }
        return null;
    }

    public boolean deleteUser(long userId) {
        if (userId > 0) {
            if (storage.containsKey(getIdPrefix(User.class) + userId)) {
                storage.remove(getIdPrefix(User.class) + userId);
                return true;
            }
        }
        return false;
    }

    public User getUserById(long id) {
        if (id > 0) {
            return (User) storage.get(getIdPrefix(User.class) + id);
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
            public boolean apply(User entity, String value) {
                return entity.getName().equals(value);
            }
        });
        return Pagination.getData(userList, pageSize, pageNum);
    }


    //EVENT
    public Event createEvent(Event event) {
        if (event != null) {
            storage.put(getIdPrefix(event) + event.getId(), event);
            return (Event) storage.get(getIdPrefix(event) + event.getId());
        }
        return null;
    }

    public Event updateEvent(Event event) {
        if (event != null) {
            return (Event) storage.put(getIdPrefix(event), event);
        }
        return null;
    }

    public boolean deleteEvent(long eventId) {
        if (eventId > 0) {
            if (storage.containsKey(getIdPrefix(Event.class) + eventId)) {
                storage.remove(getIdPrefix(Event.class) + eventId);
                return true;
            }
        }
        return false;
    }

    public Event getEventById(long id) {
        if (id > 0) {
            return (Event) storage.get(getIdPrefix(Event.class) + id);
        }
        return null;
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> eventList = CollectionUtils.filter(storage.values(), title, Event.class, new IPredicate<Event, String>() {
            @Override
            public boolean apply(Event entity, String value) {
                return entity.getTitle().equals(value);
            }
        });
        return Pagination.getData(eventList, pageSize, pageNum);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        List<Event> eventList = CollectionUtils.filter(storage.values(), day, Event.class, new IPredicate<Event, Date>() {
            @Override
            public boolean apply(Event entity, Date value) {
                return entity.getDate().equals(value);
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
        storage.put(getIdPrefix(Ticket.class) + ticket.getId(), ticket);
        return ticket;
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Ticket> ticketList = CollectionUtils.filter(storage.values(), user.getId(), Ticket.class, new IPredicate<Ticket, Long>() {
            @Override
            public boolean apply(Ticket entity, Long value) {
                return entity.getUserId() == value;
            }
        });
        return Pagination.getData(ticketList, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> ticketList = CollectionUtils.filter(storage.values(), event.getId(), Ticket.class, new IPredicate<Ticket, Long>() {
            @Override
            public boolean apply(Ticket type, Long value) {
                return type.getEventId() == value;
            }
        });

        return Pagination.getData(ticketList, pageSize, pageNum);
    }

    public boolean cancelTicket(long ticketId) {
        if (ticketId > 0) {
            if (storage.containsKey(getIdPrefix(Ticket.class) + ticketId)) {
                storage.remove(getIdPrefix(Ticket.class) + ticketId);
                return true;
            }
        }
        return false;
    }
}