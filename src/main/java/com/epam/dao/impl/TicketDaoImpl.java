package com.epam.dao.impl;

import com.epam.dao.TicketDao;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.storage.EntityStorage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TicketDaoImpl implements TicketDao {
    private static Logger logger = Logger.getLogger(TicketDaoImpl.class.getName());
    private EntityStorage storage;

    @Autowired
    public void setStorage(EntityStorage storage) {
        logger.info("@Autowired storage into TicketDaoImpl " + storage);
        this.storage = storage;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        logger.debug("bookTicket " + " userId:" + userId + " eventId:" + eventId + " place" + place + " category" + category);
        return storage.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        logger.debug("getBookedTickets " + " user:" + " pageSize:" + pageSize + " pageNum:");
        return storage.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        logger.debug("getBookedTickets " + " event:" + " pageSize:" + " pageNum:" + pageNum);
        return storage.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        logger.debug("cancelTicket " + " ticketId");
        return storage.cancelTicket(ticketId);
    }
}
