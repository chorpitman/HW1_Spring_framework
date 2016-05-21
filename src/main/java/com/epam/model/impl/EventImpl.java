package com.epam.model.impl;

import com.epam.model.Event;

import java.util.Date;

public class EventImpl implements Event {
    private static long counterId = 0;
    private long id;
    private String title;
    private Date date;

    public EventImpl() {
        this.id = ++counterId;
    }

    public EventImpl(String title, Date date) {
        this.id = ++counterId;
        this.title = title;
        this.date = date;
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
    public String toString() {
        return "EventImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
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
