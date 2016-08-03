package com.epam.advice;

import com.epam.utils.EventException;
import com.epam.utils.TicketException;
import com.epam.utils.UserAccountException;
import com.epam.utils.UserException;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice("com.epam.controller")
public class GlobalExceptionHandler {
    private static Logger log = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Throwable.class)
    public void handlerException(HttpServletResponse response, Exception ex) {
        int statusCode = 500;

        if (ex.getClass() == EventException.class) {
            statusCode = 404;
        }
        if (ex.getClass() == TicketException.class) {
            statusCode = 404;
        }

        if (ex.getClass() == UserAccountException.class) {
            statusCode = 404;
        }

        if (ex.getClass() == UserException.class) {
            statusCode = 404;
        }

        if (ex.getClass() == IllegalArgumentException.class) {
            statusCode = 404;
        }
        response.setStatus(statusCode);
        response.addHeader("Exception-type", ex.getClass().getSimpleName());
    }
}
