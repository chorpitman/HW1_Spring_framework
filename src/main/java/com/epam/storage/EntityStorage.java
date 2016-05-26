package com.epam.storage;

import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.impl.TicketImpl;

import java.util.*;

//Like DB
public class EntityStorage {
    private static final String USER_NS = "user:";
    private static final String EVENT_NS = "event:";
    private static final String TICKET_NS = "ticket:";

    //DB
    private Map<String, Object> storage = new HashMap<>(); //String key user:1, Value User

    //USER
    public User createUser(User user) {
        storage.put(USER_NS + user.getId(), user);
        return (User) storage.get(USER_NS + user.getId());
    }

    public User update(User user) {
        storage.put(USER_NS + user.getId(), user);
        return (User) storage.get(USER_NS + user.getId());
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
        return (User) storage.get(USER_NS + id);
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
        if (pageSize == 0 || name.isEmpty() || pageNum == 0) {
            return Collections.emptyList();
        }

        //получаю всех юзеров с искомым именем
        List<User> eventList = new ArrayList<>();
        for (Object user : storage.values()) {
            if (((User) user).getName().equals(name)) {
                eventList.add(((User) user));
            }
        }
        //Находим максимальное количество страниц
        int maxPageSize = 0;
        if ((eventList.size()) % pageSize == 0)
            maxPageSize = eventList.size() / pageSize;
        else {
            maxPageSize = eventList.size() / pageSize + 1;
        }
        //Условие
        if (pageNum > maxPageSize) {
            return Collections.emptyList();
        }
        //Находим начальную страницу
        int startPage = 0;
        if (maxPageSize >= 1) {
            startPage = 1;
        }
        //Находим начальный индекс элемента для добавления в возвращаемую коллекцию
        int indexFrom = (pageNum - 1) * pageSize + 1;
        //Находим последний индекс элемента для добавления в возвращаемую коллекцию
        int indexTo = pageNum * pageSize;
        if (indexTo > eventList.size()) {
            indexTo = eventList.size();
        }
        return eventList.subList(indexFrom - 1, indexTo);
    }

    //EVENT
    public Event createEvent(Event event) {
        storage.put(EVENT_NS + event.getId(), event);
        return (Event) storage.get(EVENT_NS + event.getId());
    }

    public Event updateEvent(Event event) {
        return (Event) storage.put(USER_NS + event.getId(), event);
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
        return (Event) storage.get(EVENT_NS + id);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        if (pageSize == 0 || title.isEmpty() || pageNum == 0) {
            return Collections.emptyList();
        }
        List<Event> eventList = new ArrayList<>();
        for (Object event : storage.values()) {
            if (((Event) event).getTitle().equals(title)) {
                eventList.add(((Event) event));
            }
        }
        //Находим максимальное количество страниц
        int maxPageSize = 0;
        if ((eventList.size()) % pageSize == 0)
            maxPageSize = eventList.size() / pageSize;
        else {
            maxPageSize = eventList.size() / pageSize + 1;
        }
        //Условие
        if (pageNum > maxPageSize) {
            Collections.emptyList();
        }
        //Находим начальную страницу
        int startPage = 0;
        if (maxPageSize >= 1) {
            startPage = 1;
        }
        //Находим начальный индекс элемента для добавления в возвращаемую коллекцию
        int indexFrom = (pageNum - 1) * pageSize + 1;
        //Находим последний индекс элемента для добавления в возвращаемую коллекцию
        int indexTo = pageNum * pageSize;
        if (indexTo > eventList.size()) {
            indexTo = eventList.size();
        }
        return eventList.subList(indexFrom - 1, indexTo);
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        if (pageSize == 0 || pageNum == 0) {
            return Collections.emptyList();
        }
        List<Event> eventList = new ArrayList<>();
        for (Object event : storage.values()) {
            if (((Event) event).getDate() == day) {
                eventList.add(((Event) event));
            }
        }
        int maxPageSize = 0;
        if ((eventList.size()) % pageSize == 0)
            maxPageSize = eventList.size() / pageSize;
        else {
            maxPageSize = eventList.size() / pageSize + 1;
        }
        if (pageNum > maxPageSize) {
            return Collections.emptyList();
        }
        int startPage = 0;
        if (maxPageSize >= 1) {
            startPage = 1;
        }
        int indexFrom = (pageNum - 1) * pageSize + 1;
        int indexTo = pageNum * pageSize;
        if (indexTo > eventList.size()) {
            indexTo = eventList.size();
        }
        return eventList.subList(indexFrom - 1, indexTo);
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
        if (pageSize == 0 || pageNum == 0) {
            return Collections.emptyList();
        }

        long userId = user.getId();
        List<Ticket> ticketList = new ArrayList<>();
        for (Object ticket : storage.values()) {
            if (ticket instanceof Ticket) {
                if (((Ticket) ticket).getUserId() == userId) {
                    ticketList.add((Ticket) ticket);
                }
            }
        }

        int maxPage = (ticketList.size() + pageSize - 1) / pageSize;

        if (pageNum > maxPage) {
            return Collections.emptyList();
        }

        int start = (pageNum - 1) * pageSize;

        int finish = pageNum * pageSize;
        if (finish > ticketList.size()) {
            finish = ticketList.size();
        }

        return ticketList.subList(start, finish);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        if (pageSize == 0 || pageNum == 0) {
            return Collections.emptyList();
        }

        long eventId = event.getId();
        List<Ticket> ticketList = new ArrayList<>();

        for (Object ticket : storage.values()) {
            if (ticket instanceof Ticket) {
                if (((Ticket) ticket).getEventId() == eventId) {
                    ticketList.add(((Ticket) ticket));
                }
            }
        }

        int maxPage = (ticketList.size() + pageSize - 1) / pageSize;

        if (pageNum > maxPage) {
            return Collections.emptyList();
        }

        int start = (pageNum - 1) * pageSize;

        int finish = pageNum * pageSize;
        if (finish > ticketList.size()) {
            finish = ticketList.size();
        }
        return ticketList.subList(start, finish);
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