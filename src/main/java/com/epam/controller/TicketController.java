package com.epam.controller;

import com.epam.facade.BookingFacade;
import com.epam.model.Ticket;
import com.epam.model.impl.EventImpl;
import com.epam.model.impl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = {"/ticket"})
@Controller
public class TicketController {
    private static Logger log = Logger.getLogger(TicketController.class.getName());

    @Autowired
    BookingFacade facade;

    @RequestMapping(value = {"/bookTicket"}, method = RequestMethod.POST)
    @ResponseBody
    public Ticket bookTicket(@RequestParam(value = "userId") long userId,
                             @RequestParam(value = "eventId") long eventId,
                             @RequestParam(value = "place") int place,
                             @RequestParam(value = "category") Ticket.Category category) {
        log.info("[bookTicket] : " + userId + ";" + eventId + ";" + category);
        return facade.bookTicket(userId, eventId, place, category);
    }

    @RequestMapping(value = {"/bookedUserTicket"}, method = RequestMethod.GET)
    @ResponseBody
    public List<Ticket> getBookedTickets(@RequestBody UserImpl user,
                                         @RequestParam(value = "pageSize") int pageSize,
                                         @RequestParam(value = "pageNum") int pageNum) {
        log.info("[bookedUserTicket] : " + user + ";" + pageSize + ";" + pageNum);
        return facade.getBookedTickets(user, pageSize, pageNum);
    }

    @RequestMapping(value = {"/bookedEventTicket"}, method = RequestMethod.GET)
    @ResponseBody
    public List<Ticket> getBookedTickets(@RequestBody EventImpl event,
                                         @RequestParam(value = "pageSize") int pageSize,
                                         @RequestParam(value = "pageNum") int pageNum) {
        log.info("[bookedEventTicket] : " + event + ";" + pageSize + ";" + pageNum);
        return facade.getBookedTickets(event, pageSize, pageNum);
    }

    @RequestMapping(value = {"/cancel/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public boolean cancelTicket(@PathVariable(value = "id") long ticketId) {
        log.info("[cancelTicket] : " + ticketId);
        return facade.cancelTicket(ticketId);
    }
}
