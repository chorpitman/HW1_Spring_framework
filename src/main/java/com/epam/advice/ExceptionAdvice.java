package com.epam.advice;

import com.epam.utils.UserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice("com.epam.controller")
public class ExceptionAdvice {

    @ExceptionHandler(Throwable.class)
    public void handlerException(HttpServletResponse response, Exception ex) {
        int statusCode = 500;
        if (ex.getClass() == UserException.class) {
            statusCode = 404;
        }
        response.setStatus(statusCode);
        response.addHeader("Exception-type", ex.getClass().getSimpleName());
    }
}
