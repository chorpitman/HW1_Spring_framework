package com.epam.dao.impl;

import com.epam.dao.TicketDao;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.impl.TicketImpl;
import com.epam.storage.EntityStorage;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketDaoImpl implements TicketDao {
    private static Logger logger = Logger.getLogger(TicketDaoImpl.class.getName());
    private EntityStorage storage;
    private final static String BOOK_TICKET = "";
    private final static String BOOKED_TICKETS_BY_USER = "";
    private final static String BOOKED_TICKETS_BY_EVENT = "";
    private final static String CANCEL_TICKET = "";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        logger.debug("bookTicket " + " userId:" + userId + " eventId:" + eventId + " place" + place + " category" + category);
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("userId", userId);
        namedParameters.put("eventId", eventId);
        namedParameters.put("place", place);
        namedParameters.put("category", category);
        return jdbcTemplate.queryForObject(BOOK_TICKET, namedParameters, new TicketMapper());
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        logger.debug("getBookedTickets " + " user:" + " pageSize:" + pageSize + " pageNum:");
        return jdbcTemplate.query(BOOKED_TICKETS_BY_USER, Collections.singletonMap("userId", user.getId()), new TicketMapper());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        logger.debug("getBookedTickets " + " event:" + " pageSize:" + " pageNum:" + pageNum);
        return jdbcTemplate.query(BOOKED_TICKETS_BY_EVENT, Collections.singletonMap("eventId", event.getId()), new TicketMapper());
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        logger.debug("cancelTicket " + " ticketId");
        jdbcTemplate.query(CANCEL_TICKET, Collections.singletonMap("id", ticketId), new TicketMapper());
        return true;
    }

    private static final class TicketMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
            Ticket ticket = new TicketImpl();
            ticket.setId(resultSet.getLong("id"));
            ticket.setUserId(resultSet.getLong("userId"));
            ticket.setEventId(resultSet.getLong("eventId"));
            ticket.setPlace(resultSet.getInt("place"));
            ticket.setCategory(Ticket.Category.valueOf(resultSet.getString("ticketCategory").toUpperCase()));
            return ticket;
        }
    }
}
