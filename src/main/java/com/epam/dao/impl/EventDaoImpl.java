package com.epam.dao.impl;

import com.epam.dao.EventDao;
import com.epam.model.Event;
import com.epam.model.impl.EventImpl;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EventDaoImpl implements EventDao {
    private static Logger log = Logger.getLogger(EventDaoImpl.class.getName());
    private static final String CREATE_EVENT = "INSERT INTO event (date, title, ticketPrice) " +
            "value (:date, :title, :ticketPrice)";
    private static final String GET_EVENT_BY_ID = "SELECT * FROM event WHERE id = :id";
    private static final String UDATE_EVENT = "UPDATE event SET " +
            "date = :date, title = :title, ticketPrice = :ticketPrice WHERE id = :id";
    private static final String DELETE_EVENT = "DELETE FROM event WHERE id =:id";
    private static final String GET_EVENTS_BY_TITLE = "SELECT * FROM event WHERE title = :title";
    private static final String GET_EVENTS_FOR_DAY = "SELECT * FROM event WHERE date = :date";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Event getEventById(long eventId) {
        log.debug("getEventById:" + eventId);
        return jdbcTemplate.queryForObject(GET_EVENT_BY_ID, Collections.singletonMap("id", eventId), new EventMapper());
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.debug("getEventsByTitle:" + title + " pageSize:" + pageSize + " pageNum:" + pageNum);
        int start = pageSize;
        int fiish = (pageNum - 1) * pageSize;
        String sql = GET_EVENTS_BY_TITLE + " LIMIT " + start + " OFFSET " + fiish;
        return jdbcTemplate.query(sql, Collections.singletonMap("title", title), new EventMapper());
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.debug("getEventsForDay:" + day + " pageSize:" + pageSize + " pageNum:" + pageNum);
        int start = pageSize;
        int fiish = (pageNum - 1) * pageSize;
        String sql = GET_EVENTS_FOR_DAY + " LIMIT " + start + " OFFSET " + fiish;
        return jdbcTemplate.query(sql, Collections.singletonMap("date", day), new EventMapper());
    }

    @Override
    public Event createEvent(Event event) {
        log.debug("createEvent:" + event);
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("date", event.getDate());
        namedParameters.put("title", event.getTitle());
        namedParameters.put("ticketPrice", event.getTicketPrice());
        jdbcTemplate.update(CREATE_EVENT, namedParameters);
        return event;
    }

    @Override
    public Event updateEvent(Event event) {
        log.debug("updateEvent:" + event);
        Map<String, Object> nameParameters = new HashMap<>();
        nameParameters.put("id", event.getId());
        nameParameters.put("date", event.getDate());
        nameParameters.put("title", event.getTitle());
        nameParameters.put("ticketPrice", event.getTicketPrice());
        jdbcTemplate.update(UDATE_EVENT, nameParameters);
        return event;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        log.debug("deleteEvent:" + eventId);
        jdbcTemplate.update(DELETE_EVENT, Collections.singletonMap("id", eventId));
        return true;
    }

    private static final class EventMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            Event event = new EventImpl();
            event.setId(resultSet.getLong("id"));
            event.setDate(resultSet.getDate("date"));
            event.setTitle(resultSet.getString("title"));
            event.setTicketPrice(resultSet.getBigDecimal("ticketPrice"));
            return event;
        }
    }
}
