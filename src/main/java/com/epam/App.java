package com.epam;

import com.epam.facade.BookingFacade;
import com.epam.model.Ticket;
import com.epam.model.User;
import com.epam.model.impl.TicketImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext appCtx = new ClassPathXmlApplicationContext("context.xml");
        BookingFacade facade = appCtx.getBean("bookingFacade", BookingFacade.class);

        User user = appCtx.getBean("user", User.class);
        User createdUser = facade.createUser(user);
        User foundUser = facade.getUserById(createdUser.getId());

        System.out.println(createdUser);
        System.out.println(foundUser);

//        TEST ENUM
//        Ticket ticket = new TicketImpl();
//        ticket.setCategory(Ticket.Category.STANDARD);
    }
}
