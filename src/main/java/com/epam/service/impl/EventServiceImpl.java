package com.epam.service.impl;

import com.epam.dao.EventDao;
import com.epam.model.Event;
import com.epam.service.EventService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EventServiceImpl implements EventService {
    private EventDao eventDao;

    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Event getEventById(long eventId) {
        return eventDao.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        if (pageSize <= 0 || pageNum <= 0) {
            return Collections.emptyList();
        }
        return eventDao.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        if (pageSize <= 0 || pageNum <= 0) {
            return Collections.emptyList();
        }
        return eventDao.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        return eventDao.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        return eventDao.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return eventDao.deleteEvent(eventId);
    }
}
