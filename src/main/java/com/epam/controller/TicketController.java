package com.epam.controller;

import com.epam.facade.BookingFacade;
import org.springframework.beans.factory.annotation.Autowired;

public class TicketController {

    @Autowired
    BookingFacade facade;
}
