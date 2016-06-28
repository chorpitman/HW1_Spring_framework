package com.epam.model.impl;

import com.epam.model.Event;

import java.math.BigDecimal;
import java.util.Date;

public class EventImpl implements Event {
    private static long counterId = 0;

    private long id;
    private String title;
    private Date date;
    private BigDecimal ticketPrice;

    public EventImpl() {

    }

    public EventImpl(long id, String title, Date date, BigDecimal ticketPrice) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.ticketPrice = ticketPrice;
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
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "EventImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", ticketPrice=" + ticketPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventImpl event = (EventImpl) o;

        if (id != event.id) return false;
        if (!title.equals(event.title)) return false;
        return date.equals(event.date);

    }
}
