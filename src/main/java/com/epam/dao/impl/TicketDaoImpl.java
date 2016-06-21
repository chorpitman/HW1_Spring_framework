package com.epam.dao.impl;

import com.epam.dao.TicketDao;
import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.impl.TicketImpl;
import org.apache.log4j.Logger;
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
    private static Logger logger = Logger.getLogger(TicketDaoImpl.class.getName());

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
        logger.debug("bookedTicketById " + " ticketId:" + ticketId);
        return jdbcTemplate.queryForObject(BOOKED_TICKETS_BY_ID, Collections.singletonMap("id", ticketId), new TicketMapper());
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        logger.debug("bookTicket " + " userId:" + userId + " eventId:" + eventId + " place" + place + " category" + category);
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("userId", userId);
        namedParameters.put("eventId", eventId);
        namedParameters.put("place", place);
        namedParameters.put("category", category.toString());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(BOOK_TICKET, new MapSqlParameterSource(namedParameters), keyHolder);
        int lastBookId = keyHolder.getKey().intValue();
        return jdbcTemplate.queryForObject(BOOKED_TICKETS_BY_ID, Collections.singletonMap("id", lastBookId), new TicketMapper());
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        logger.debug("getBookedTickets " + " user:" + " pageSize:" + pageSize + " pageNum:");
        int start = pageSize;
        int finish = (pageNum - 1) * pageSize;
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("userId", user.getId());
        namedParameters.put("pageSize", pageSize);
        namedParameters.put("pageNum", pageNum);
        namedParameters.put("start", start);
        namedParameters.put("finish", finish);
        return jdbcTemplate.query(BOOKED_TICKETS_BY_USER, new MapSqlParameterSource(namedParameters), new TicketMapper());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        logger.debug("getBookedTickets " + " event:" + " pageSize:" + " pageNum:" + pageNum);
        int start = pageSize;
        int finish = (pageNum - 1) * pageSize;
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("eventId", event.getId());
        namedParameters.put("pageSize", pageSize);
        namedParameters.put("pageNum", pageNum);
        namedParameters.put("start", start);
        namedParameters.put("finish", finish);
        return jdbcTemplate.query(BOOKED_TICKETS_BY_EVENT, new MapSqlParameterSource(namedParameters), new TicketMapper());
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        logger.debug("cancelTicket " + " ticketId");
        if (jdbcTemplate.update(CANCEL_TICKET, Collections.singletonMap("id", ticketId)) > 0) {
            return true;
        }
        return false;
    }

    private static final class TicketMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
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
