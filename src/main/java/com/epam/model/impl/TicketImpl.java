package com.epam.model.impl;

import com.epam.model.Ticket;

public class TicketImpl implements Ticket {
    private static long counterId = 0;

    private long id;
    private long eventId;
    private long userId;
    private int place;
    private Category category;

    public TicketImpl() {
    }

    public TicketImpl(long id, Category category, long eventId, long userId, int place) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.place = place;
        this.category = category;
    }

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
        return eventId;
    }

    @Override
    public void setEventId(long eventId) {
//        event.setId(eventId);
        this.eventId = eventId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
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
                ", event=" + eventId +
                ", user=" + userId +
                ", place=" + place +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketImpl ticket = (TicketImpl) o;

        if (id != ticket.id) return false;
        if (eventId != ticket.eventId) return false;
        if (userId != ticket.userId) return false;
        if (place != ticket.place) return false;
        return category == ticket.category;

    }
}
