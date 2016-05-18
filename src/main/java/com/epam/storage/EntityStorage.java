package com.epam.storage;

import com.epam.model.Event;
import com.epam.model.User;

import java.util.*;

//Like DB
public class EntityStorage {
    private static final String USER_NS = "user:";
    private static final String EVENT_NS = "event:";
    private static final String TICKET_NS = "ticket:";

    private Map<String, Object> userMap = new HashMap<>(); //String key user:1, Value User

    //USER
    public User createUser(User user) {
        return (User) userMap.put(USER_NS + user.getId(), user);
    }

    public User update(User user) {
        return (User) userMap.put(USER_NS + user.getId(), user);
    }

    public boolean deleteUser(long userId) {
        for (String user : userMap.keySet()) {
            if (user == USER_NS + userId) {
                userMap.remove(user);
                return true;
            }
        }
        return false;
    }

    public User getUserById(long id) {
        return (User) userMap.get(USER_NS + id);
    }

    public User getUserByEmail(String email) {
        for (Object user : userMap.values()) {
            if (((User) user).getEmail() == email) {
                return (User) user;
            }
        }
        return null;
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        //получаю всех юзеров с искомым именем
        List<User> eventList = new ArrayList<>();
        for (Object user : userMap.values()) {
            if (((User) user).getName().equals(name)) {
                eventList.add(((User) user));
            }
        }
        if (pageSize == 0 || name.isEmpty() || pageNum == 0) {
            System.out.println("Wrong param");
            return Collections.emptyList();
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
            System.out.println("Wrong param - page size");
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
        return (Event) userMap.put(EVENT_NS + event.getId(), event);
    }

    public Event updateEvent(Event event) {
        return (Event) userMap.put(USER_NS + event.getId(), event);
    }

    public boolean deleteEvent(long eventId) {
        for (String event : userMap.keySet()) {
            if (event == EVENT_NS + eventId) {
                userMap.remove(event);
                return true;
            }
        }
        return false;
    }

    public Event getEventById(long id) {
        return (Event) userMap.get(EVENT_NS + id);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        List<Event> eventList = new ArrayList<>();
        for (Object event : userMap.values()) {
            if (((Event) event).getTitle() == title) {
                eventList.add(((Event) event));
            }
        }
        if (pageSize == 0 || title.isEmpty() || pageNum == 0) {
            System.out.println("Wrong param");
            return Collections.emptyList();
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
            System.out.println("Wrong param - page size");
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

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        List<Event> eventList = new ArrayList<>();
        for (Object event : userMap.values()) {
            if (((Event) event).getDate() == day) {
                eventList.add(((Event) event));
            }
        }
        if (pageSize == 0 || pageNum == 0) {
            System.out.println("Wrong param");
            return Collections.emptyList();
        }
        int maxPageSize = 0;
        if ((eventList.size()) % pageSize == 0)
            maxPageSize = eventList.size() / pageSize;
        else {
            maxPageSize = eventList.size() / pageSize + 1;
        }
        if (pageNum > maxPageSize) {
            System.out.println("Wrong param - page size");
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
}