package com.epam.dao.impl;

import com.epam.dao.EventDao;
import com.epam.model.Event;
import com.epam.storage.EntityStorage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class EventDaoImpl implements EventDao {
    private static Logger log = Logger.getLogger(EventDaoImpl.class.getName());
    private EntityStorage storage;

    @Autowired
    public void setStorage(EntityStorage storage) {
        log.info("@Autowired storage into EventDaoImpl " + storage);
        this.storage = storage;
    }

    @Override
    public Event getEventById(long eventId) {
        log.debug("getEventById:" + eventId);
        return storage.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.debug("getEventsByTitle:" + title + " pageSize:" + pageSize + " pageNum:" + pageNum);
        return storage.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.debug("getEventsForDay:" + day + " pageSize:" + pageSize + " pageNum:" + pageNum);
        return storage.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        log.debug("createEvent:" + event);
        return storage.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        log.debug("updateEvent:" + event);
        return storage.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        log.debug("deleteEvent:" + eventId);
        return storage.deleteEvent(eventId);
    }
}
