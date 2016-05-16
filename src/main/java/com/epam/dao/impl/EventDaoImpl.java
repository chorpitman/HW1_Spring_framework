package com.epam.dao.impl;

import com.epam.dao.EventDao;
import com.epam.model.Event;
import com.epam.storage.EntityStorage;

import java.util.Date;
import java.util.List;

public class EventDaoImpl implements EventDao {

    private EntityStorage storage;


    @Override
    public Event getEventById(long eventId) {
        return null;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return null;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return null;
    }

    @Override
    public Event createEvent(Event event) {
        return null;
    }

    @Override
    public Event updateEvent(Event event) {
        return null;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        return false;
    }
}
