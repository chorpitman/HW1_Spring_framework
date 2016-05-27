package com.epam.utils;

import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;

import java.util.*;

public class Pagination {

    public static List<User> usersPagination(Map<String, Object> storage, String name, int pageSize, int pageNum) {
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

    public static List<Event> eventsPaginationByTitle(Map<String, Object> storage, String title, int pageSize, int pageNum) {
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
  /*      //Находим начальную страницу
        int startPage = 0;
        if (maxPageSize >= 1) {
            startPage = 1;
        }*/
        //Находим начальный индекс элемента для добавления в возвращаемую коллекцию
        int indexFrom = (pageNum - 1) * pageSize + 1;
        //Находим последний индекс элемента для добавления в возвращаемую коллекцию
        int indexTo = pageNum * pageSize;
        if (indexTo > eventList.size()) {
            indexTo = eventList.size();
        }
        return eventList.subList(indexFrom - 1, indexTo);
    }

    public static List<Event> eventsPaginationForDay(Map<String, Object> storage, Date day, int pageSize, int pageNum) {
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
        if ((eventList.size()) % pageSize == 0) {
            maxPageSize = eventList.size() / pageSize;
        } else {
            maxPageSize = eventList.size() / pageSize + 1;
        }

        if (pageNum > maxPageSize) {
            return Collections.emptyList();
        }

    /*    int startPage = 0;
        if (maxPageSize >= 1) {
            startPage = 1;
        }*/

        int indexFrom = (pageNum - 1) * pageSize + 1;
        int indexTo = pageNum * pageSize;
        if (indexTo > eventList.size()) {
            indexTo = eventList.size();
        }
        return eventList.subList(indexFrom - 1, indexTo);
    }

    public static List<Ticket> bookTicketByUserPagination(Map<String, Object> storage, User user, int pageSize, int pageNum) {
        if (pageSize == 0 || pageNum == 0) {
            return Collections.emptyList();
        }

        long userId = user.getId();
        List<Ticket> ticketList = new ArrayList<>();
        for (Object entity : storage.values()) {
            if (entity instanceof Ticket) {
                Ticket ticket = (Ticket) entity;
                if (ticket.getUserId() == userId) {
                    ticketList.add(ticket);
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

    public static List<Ticket> bookTicketByEventPagination(Map<String, Object> storage, Event event, int pageSize, int pageNum) {
        if (pageSize == 0 || pageNum == 0) {
            return Collections.emptyList();
        }

        long eventId = event.getId();
        List<Ticket> ticketList = new ArrayList<>();
        for (Object entity : storage.values()) {
            if (entity instanceof Ticket) {
                Ticket ticket = (Ticket) entity;
                if (ticket.getEventId() == eventId) {
                    ticketList.add(ticket);
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

    /*interface Filter<T> {
        List<T> apply(Collection<Object> objects);
    }

    class A implements Filter<Ticket> {

        @Override
        public List<Ticket> apply(Collection<Object> objects) {
            List<Ticket> ticketList = new ArrayList<>();
            for (Object entity : objects) {
                if (entity instanceof Ticket) {
                    Ticket ticket = (Ticket) entity;
                    if (ticket.getEventId() == eventId) {
                        ticketList.add(ticket);
                    }
                }
            }
            return ticketList;
        }
    }*/
}