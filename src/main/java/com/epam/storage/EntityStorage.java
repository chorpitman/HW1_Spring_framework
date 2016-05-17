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


    //Todo pagination impl
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        //получаю всех юзеров с искомым именем
        List<User> matchingUserList = new ArrayList<>();
        for (Object user : userMap.values()) {
            if (((User) user).getName().equals(name)) {
                matchingUserList.add(((User) user));
            }
        }

        //Находим максимальное количество страниц
        int maxPageSize = 0;
        if (matchingUserList.size() % pageSize == 0)
            maxPageSize = matchingUserList.size() / pageSize;
        else {
            maxPageSize = matchingUserList.size() / pageSize + 1;
        }
        //Находим начальную страницу
        int startPage = 0;
        if (maxPageSize >= 1) {
            startPage = 1;
        }

        int indexFrom = 0;
        int indexTo = 0;

        return matchingUserList.subList(indexFrom, indexTo);
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

    // TODO: 16.05.2016 pagination
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return null;
    }

    // TODO: 16.05.2016 pagination
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return null;
    }

    //TICKETS
}