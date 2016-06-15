package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.model.Event;
import com.epam.model.Ticket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Oleg_Chorpita on 6/15/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:drop.sql", "classpath:ddl_InMem.sql", "classpath:dml_InMem.sql"})
public class TransactionTest {

    UserService userService;
    EventService eventService;
    TicketService ticketService;
    UserAccountService userAccountService;

    @Test
    public void testBookTicket() {
        userAccountService.rechargeAccount(2, new BigDecimal(50));
        Ticket bookedTicket = ticketService.bookTicket(2, 2, 20, Ticket.Category.BAR);
        assertNotNull(bookedTicket);

        Event receivedEvent = eventService.getEventById(2);
        assertEquals(new BigDecimal(201), receivedEvent.getTicketPrice());
    }
}
