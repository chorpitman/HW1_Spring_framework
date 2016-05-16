package com.epam.storage;

import com.epam.model.Event;
import com.epam.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Like DB
public class EntityStorage {
    private static final String USER_NS = "user:";
    private static final String EVENT_NS = "event:";
    private static final String TICKET_NS = "ticket:";

    private Map<String, Object> userMap = new HashMap<>(); //String key user:1, Value User

    //USER
    public User getUser(long id) {
        return (User) userMap.get(USER_NS + id);
    }

    public User addUser(User user) {
        return (User) userMap.put(USER_NS + user.getId(), user);
    }

    public User getUserByEmail(String email) {
        for (Object user : userMap.values()) {
            if (((User) user).getEmail() == email) {
                return (User) user;
            }
        }
        return null;
    }

    //todo impl
    public User update(User user) {
        for (String mapKey : userMap.keySet()) {
            if (mapKey == USER_NS + user.getId()) {
                userMap.put(USER_NS + user.getId(), user);
                return user;
            }
        }
        return (User) userMap.get(user);
    }

    //todo impl
    public boolean deleteUser(long userId) {
        return userMap.isEmpty();
    }

    //Todo pagination impl
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {

//
//        if (name.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        List<User> listUserName = new ArrayList<>();
//        int segment = userMap.size() / pageSize;
//
//        if (segment <= 1) {
//            for (User user : userMap.values()) {
//                user.getName(name);
//                listUserName.add(name);
//            }
//            return listUserName;
//        }
//
//        //вычислить какой диапазон вернуть
//        int from = ;
//        int to = ;
        return null;
    }

    //EVENT
    public Event getEventById(long id) {
        return (Event) userMap.get(EVENT_NS + id);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return null;
    }

    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return null;
    }

    public Event createEvent(Event event) {
        return (Event) userMap.put(EVENT_NS + event.getId(), event);
    }

    public Event updateEvent(Event event) {
        return (Event) userMap.get(event);
    }

    public boolean deleteEvent(long eventId) {
        return false;
    }
}
