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

//FIXME this bean should be injected using auto-wiring
//Like DB
public class EntityStorage {

    //USER
    public User createUser(User user) {
        storage.put(getIdPrefix(user) + user.getId(), user);
        return (User) storage.get(getIdPrefix(user) + user.getId());
    }

    public User update(User user) {
        storage.put(getIdPrefix(user) + user.getId(), user);
        return (User) storage.get(getIdPrefix(user) + user.getId());
    }

    public boolean deleteUser(long userId) {
        if (storage.containsKey(getIdPrefix(User.class) + userId)) {
            storage.remove(getIdPrefix(User.class) + userId);
            return true;
        }
        return false;
    }

    public User getUserById(long id) {
        return (User) storage.get(getIdPrefix(User.class) + id);
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
        storage.put(getIdPrefix(event) + event.getId(), event);
        return (Event) storage.get(getIdPrefix(event) + event.getId());
    }

    public Event updateEvent(Event event) {
        return (Event) storage.put(getIdPrefix(event), event);
    }

    public boolean deleteEvent(long eventId) {
        if (storage.containsKey(getIdPrefix(Event.class) + eventId)) {
            storage.remove(getIdPrefix(Event.class) + eventId);
            return true;
        }
        return false;
    }

    public Event getEventById(long id) {
        return (Event) storage.get(getIdPrefix(Event.class) + id);
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
        if (storage.containsKey(getIdPrefix(Ticket.class) + ticketId)) {
            storage.remove(getIdPrefix(Ticket.class) + ticketId);
            return true;
        }
        return false;
    }

    //DB
    private Map<String, Object> storage = new HashMap<>();

    //Prefix
    private <T> String getIdPrefix(T t) {
        return t.getClass().getInterfaces()[0].getSimpleName().toLowerCase() + ":";
    }

    private <T> String getIdPrefix(Class<T> clazz) {
        return clazz.getSimpleName().toLowerCase() + ":";
    }
}