package com.epam.utils;

public class EventException extends RuntimeException {

    //    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Employee Not Found") //404
    public EventException() {

    }

    public EventException(String message) {
        super(message);
    }

    public EventException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventException(Throwable cause) {
        super(cause);
    }

    protected EventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
