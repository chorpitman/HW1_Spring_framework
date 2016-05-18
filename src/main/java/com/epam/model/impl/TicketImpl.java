package com.epam.model.impl;

import com.epam.model.Event;
import com.epam.model.Ticket;
import com.epam.model.User;

public class TicketImpl implements Ticket {
    private Category category;

    private long id;
    private Event event;
    private User user;
    private int place;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getEventId() {
        return event.getId();
    }

    @Override
    public void setEventId(long eventId) {
        event.setId(eventId);
    }

    @Override
    public long getUserId() {
        return user.getId();
    }

    @Override
    public void setUserId(long userId) {
        user.setId(userId);
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int getPlace() {
        return place;
    }


    @Override
    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "TicketImpl{" +
                "category=" + category +
                ", id=" + id +
                ", event=" + event +
                ", user=" + user +
                ", place=" + place +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketImpl ticket = (TicketImpl) o;

        if (id != ticket.id) return false;
        if (place != ticket.place) return false;
        if (category != ticket.category) return false;
        if (!event.equals(ticket.event)) return false;
        return user.equals(ticket.user);

    }
}
