package com.epam.dao.impl;

import com.epam.dao.EventDao;
import com.epam.model.Event;
import com.epam.model.impl.EventImpl;
import com.epam.utils.EventException;
import com.epam.utils.Validator;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EventDaoImpl implements EventDao {
    private static Logger log = Logger.getLogger(EventDaoImpl.class.getName());

    private static final String CREATE_EVENT = "INSERT INTO event (date, title, ticketPrice) " +
            "VALUES (:date, :title, :ticketPrice)";
    private static final String GET_EVENT_BY_ID = "SELECT * FROM event WHERE id = :id";
    private static final String UPDATE_EVENT = "UPDATE event SET " +
            "date = :date, title = :title, ticketPrice = :ticketPrice WHERE id = :id";
    private static final String DELETE_EVENT = "DELETE FROM event WHERE id =:id";
    private static final String GET_EVENTS_BY_TITLE = "SELECT * FROM event WHERE title = :title LIMIT :start OFFSET :finish";
    private static final String GET_EVENTS_FOR_DAY = "SELECT * FROM event WHERE date = :date LIMIT :start OFFSET :finish";

    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Event getEventById(long eventId) {
        log.debug("getEventById:" + eventId);
        Event receivedEvent;
        try {
            receivedEvent = jdbcTemplate.queryForObject(GET_EVENT_BY_ID, Collections.singletonMap("id", eventId), new EventMapper());
        } catch (DataAccessException e) {
            throw new EventException("event with [id]:" + eventId + " doesn't exist");
        }
        Validator.checkNotNull(receivedEvent);
        return receivedEvent;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        log.debug("getEventsByTitle:" + title + " pageSize:" + pageSize + " pageNum:" + pageNum);
        int finish = (pageNum - 1) * pageSize;
        Map<String, Object> nameParameters = new HashMap<>();
        nameParameters.put("title", title);
        nameParameters.put("start", pageSize);
        nameParameters.put("finish", finish);
        List<Event> receivedEvent = jdbcTemplate.query(GET_EVENTS_BY_TITLE, new MapSqlParameterSource(nameParameters), new EventMapper());
        if (receivedEvent.isEmpty()) {
            throw new EventException("Events with [title]: " + title + " doesn't exist");
        }
        Validator.checkNotNull(receivedEvent);
        return receivedEvent;
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        log.debug("getEventsForDay:" + day + " pageSize:" + pageSize + " pageNum:" + pageNum);
        int finish = (pageNum - 1) * pageSize;
        Map<String, Object> nameParameters = new HashMap<>();
        nameParameters.put("date", day);
        nameParameters.put("start", pageSize);
        nameParameters.put("finish", finish);
        List<Event> receivedEvent;
        receivedEvent = jdbcTemplate.query(GET_EVENTS_FOR_DAY, new MapSqlParameterSource(nameParameters), new EventMapper());

        if (receivedEvent.isEmpty()) {
            throw new EventException("Events with [date]: " + day + " doesn't exist");
        }
        Validator.checkNotNull(receivedEvent);
        return receivedEvent;
    }

    @Override
    public Event createEvent(Event event) {
        log.debug("createEvent:" + event);
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("date", event.getDate());
        namedParameters.put("title", event.getTitle());
        namedParameters.put("ticketPrice", event.getTicketPrice());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(CREATE_EVENT, new MapSqlParameterSource(namedParameters), keyHolder);
        int lastEventId = keyHolder.getKey().intValue();
        Event receivedEvent = jdbcTemplate.queryForObject(GET_EVENT_BY_ID, Collections.singletonMap("id", lastEventId), new EventMapper());
        Validator.checkNotNull(receivedEvent);
        return receivedEvent;
    }

    @Override
    public Event updateEvent(Event event) {
        log.debug("updateEvent:" + event);
        //check event in DB
        getEventById(event.getId());
        Map<String, Object> nameParameters = new HashMap<>();
        nameParameters.put("id", event.getId());
        nameParameters.put("date", event.getDate());
        nameParameters.put("title", event.getTitle());
        nameParameters.put("ticketPrice", event.getTicketPrice());
        jdbcTemplate.update(UPDATE_EVENT, nameParameters);
        return event;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        log.debug("deleteEvent:" + eventId);
        //check DB
        getEventById(eventId);
        return jdbcTemplate.update(DELETE_EVENT, Collections.singletonMap("id", eventId)) > 0;
    }

    private static final class EventMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            log.debug("mapRow:" + resultSet + " " + i);
            Event event = new EventImpl();
            event.setId(resultSet.getLong("id"));
            event.setDate(resultSet.getDate("date"));
            event.setTitle(resultSet.getString("title"));
            event.setTicketPrice(resultSet.getBigDecimal("ticketPrice"));
            return event;
        }
    }
}
