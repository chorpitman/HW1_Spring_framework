package com.epam.controller;

import com.epam.facade.BookingFacade;
import com.epam.model.Event;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = {"/event", "/Event"})
public class EventController {
    private static Logger log = Logger.getLogger(EventController.class.getName());

    @Autowired
    BookingFacade facade;

    @RequestMapping(value = "/get/", method = RequestMethod.GET)
    public Event getEventById(long eventId) {
        log.info("getEventById: " + eventId);
        return facade.getEventById(eventId);
    }

    @RequestMapping(value = "/title/", method = RequestMethod.GET)
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.info("getEventsByTitle: " + title + pageSize + pageNum);
        return facade.getEventsByTitle(title, pageSize, pageNum);
    }

    @RequestMapping(value = "/day/", method = RequestMethod.GET)
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return facade.getEventsForDay(day, pageSize, pageNum);
    }

    @RequestMapping(value = "/create/", method = RequestMethod.POST)
    public Event createEvent(Event event) {
        return facade.createEvent(event);
    }

    @RequestMapping(value = "/update/", method = RequestMethod.PUT)
    public Event updateEvent(Event event) {
        return facade.updateEvent(event);
    }

    @RequestMapping(value = "/delete/", method = RequestMethod.GET)
    // TODO: 14.07.16 think about methods
    public boolean deleteEvent(long eventId) {
        return facade.deleteEvent(eventId);
    }
}
