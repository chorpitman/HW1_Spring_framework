package com.epam.dao.impl;

import com.epam.dao.EventDao;
import com.epam.model.Event;
import com.epam.model.impl.EventImpl;
import com.epam.storage.EntityStorage;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EventDaoImpl implements EventDao {
    private static Logger log = Logger.getLogger(EventDaoImpl.class.getName());
    private EntityStorage storage;

    private static final String GET_EVENT_BY_ID = "SELECT * FROM event WHERE id = :id";
    private static final String CREATE_EVENT = "INSERT INTO event (data, title, ticketPrice) " +
                                                "value (:date, :title, :ticketPrice)";
    private static final String UDATE_EVENT = "";

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
        Map namedParameters = new HashMap<>();
        namedParameters.put("data", event.getDate());
        namedParameters.put("title", event.getTitle());
        namedParameters.put("ticketPrice", event.getTicketPrice());
        jdbcTemplate.update(CREATE_EVENT, namedParameters);
        return event;
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

    private static final class EventMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            Event event = new EventImpl();
            event.setId(resultSet.getLong("id"));
            event.setDate(resultSet.getDate("date"));
            event.setTitle(resultSet.getString("title"));
            return event;
        }
    }
}
