package com.epam.dao.impl;

import com.epam.dao.TicketDao;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.impl.TicketImpl;
import com.epam.utils.TicketException;
import com.epam.utils.Validator;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketDaoImpl implements TicketDao {
    private static Logger log = Logger.getLogger(TicketDaoImpl.class.getName());

    private final static String BOOKED_TICKETS_BY_ID = "SELECT * FROM Ticket WHERE id = :id";
    private final static String BOOK_TICKET = "INSERT INTO Ticket (userId, eventId, place, category) VALUES (:userId, :eventId, :place, :category)";
    private final static String BOOKED_TICKETS_BY_USER = "SELECT * FROM Ticket WHERE userId = :userId LIMIT :start OFFSET :finish";
    private final static String BOOKED_TICKETS_BY_EVENT = "SELECT * FROM Ticket WHERE eventId = :eventId LIMIT :start OFFSET :finish";
    private final static String CANCEL_TICKET = "DELETE FROM Ticket WHERE id =:id";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Ticket bookedTicketById(long ticketId) {
        log.debug("bookedTicketById " + " ticketId:" + ticketId);
        Ticket receivedTicket;
        try {
            receivedTicket = jdbcTemplate.queryForObject(BOOKED_TICKETS_BY_ID, Collections.singletonMap("id", ticketId), new TicketMapper());
        } catch (DataAccessException e) {
            throw new TicketException("ticket with [id]:" + ticketId + " doesn't exist");
        }
        Validator.checkNotNull(receivedTicket);
        return receivedTicket;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        log.debug("bookTicket " + " userId:" + userId + " eventId:" + eventId + " place" + place + " category" + category);
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("userId", userId);
        namedParameters.put("eventId", eventId);
        namedParameters.put("place", place);
        namedParameters.put("category", category.toString());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(BOOK_TICKET, new MapSqlParameterSource(namedParameters), keyHolder);
        int lastBookId = keyHolder.getKey().intValue();
        Ticket receivedTicket = jdbcTemplate.queryForObject(BOOKED_TICKETS_BY_ID, Collections.singletonMap("id", lastBookId), new TicketMapper());
        Validator.checkNotNull(receivedTicket);
        return receivedTicket;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        log.debug("getBookedTickets " + " user:" + " pageSize:" + pageSize + " pageNum:");
        int finish = (pageNum - 1) * pageSize;
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("userId", user.getId());
        namedParameters.put("start", pageSize);
        namedParameters.put("finish", finish);
        List<Ticket> receivedTickets = jdbcTemplate.query(BOOKED_TICKETS_BY_USER, new MapSqlParameterSource(namedParameters), new TicketMapper());
        if (receivedTickets.isEmpty()) {
            throw new TicketException("when you have booked tickets error occurred" + user);
        }
        Validator.checkNotNull(receivedTickets);
        return receivedTickets;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        log.debug("getBookedTickets " + " event:" + " pageSize:" + " pageNum:" + pageNum);
        int finish = (pageNum - 1) * pageSize;
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("eventId", event.getId());
        namedParameters.put("start", pageSize);
        namedParameters.put("finish", finish);
        List<Ticket> receivedTickets = jdbcTemplate.query(BOOKED_TICKETS_BY_EVENT, new MapSqlParameterSource(namedParameters), new TicketMapper());
        if (receivedTickets.isEmpty()) {
            throw new TicketException("when you have booked tickets error occurred: " + event);
        }
        Validator.checkNotNull(receivedTickets);
        return receivedTickets;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        log.debug("cancelTicket " + " ticketId");
        bookedTicketById(ticketId);
        return jdbcTemplate.update(CANCEL_TICKET, Collections.singletonMap("id", ticketId)) > 0;
    }

    private static final class TicketMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
            log.debug("mapRow:" + resultSet + " " + i);
            Ticket ticket = new TicketImpl();
            ticket.setId(resultSet.getLong("id"));
            ticket.setUserId(resultSet.getLong("userId"));
            ticket.setEventId(resultSet.getLong("eventId"));
            ticket.setPlace(resultSet.getInt("place"));
            ticket.setCategory(Ticket.Category.valueOf(resultSet.getString("category").toUpperCase()));
            return ticket;
        }
    }
}
