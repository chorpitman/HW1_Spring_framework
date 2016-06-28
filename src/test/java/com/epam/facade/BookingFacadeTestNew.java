package com.epam.facade;

import com.epam.config.ServiceTestConfig;
import com.epam.facade.impl.BookingFacadeImpl;
import com.epam.model.Event;
import com.epam.model.impl.EventImpl;
import com.epam.service.EventService;
import com.epam.service.TicketService;
import com.epam.service.UserAccountService;
import com.epam.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class BookingFacadeTestNew {
    private Event event;

    private BookingFacade bookingFacade;


    @Autowired
    EventService eventService;

    @Autowired
    UserService userService;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserAccountService userAccountService;


    @Before
    public void init() {
        bookingFacade = new BookingFacadeImpl(userService, eventService, ticketService, userAccountService);
//        event = new EventImpl()
    }


    @Test
    public void testGetEventById() {
        event = bookingFacade.getEventById(1);
        assertNotNull(event);
        assertEquals(1L, event.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEventByIdWithWrongParam() {
        bookingFacade.getEventById(0);
        bookingFacade.getEventById(-1);
    }

    @Test
    public void testGetEventsByTitle() {
        String title = "VELODAY";
        int pageSize = 1;
        int pageNum = 1;
        List<Event> eventList = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        assertNotNull(eventList);
        assertEquals(1, eventList.size());

        pageSize = 3;
        pageNum = 1;
        List<Event> eventList1 = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        assertNotNull(eventList);
        assertEquals(3, eventList1.size());

        pageSize = 2;
        pageNum = 2;
        List<Event> eventList2 = bookingFacade.getEventsByTitle(title, pageSize, pageNum);
        System.out.println(eventList2);
        assertNotNull(eventList);
        assertEquals(1, eventList2.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEventsByTitleWithWrongParam() {
        String title = "VELODAY";
        int pageSize = 0;
        int pageNum = 0;

        bookingFacade.getEventsByTitle(title, pageSize, pageNum);

        pageSize = -1;
        pageNum = -1;
        bookingFacade.getEventsByTitle(title, pageSize, pageNum);

        pageSize = 1;
        pageNum = -1;
        bookingFacade.getEventsByTitle(title, pageSize, pageNum);

        pageSize = -1;
        pageNum = 1;
        bookingFacade.getEventsByTitle(title, pageSize, pageNum);

        pageSize = 1;
        pageNum = 0;
        bookingFacade.getEventsByTitle(title, pageSize, pageNum);

        pageSize = 0;
        pageNum = 1;
        bookingFacade.getEventsByTitle(title, pageSize, pageNum);

        pageSize = 0;
        pageNum = 1;
        bookingFacade.getEventsByTitle(null, pageSize, pageNum);
    }

    @Test
    public void testGetEventsForDay() throws ParseException {
        String date = "2016-05-01";
        Date eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        int pageSize = 1;
        int pageNum = 1;

        List<Event> eventList = bookingFacade.getEventsForDay(eventDate, pageSize, pageNum);
        assertNotNull(eventList);
        assertEquals(1, eventList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEventsForDayWithWtongParam() throws Exception {
        String date = "2016-05-01";
        Date eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        int pageSize = 0;
        int pageNum = 0;

        bookingFacade.getEventsForDay(eventDate, pageSize, pageNum);

        pageSize = -1;
        pageNum = -1;
        bookingFacade.getEventsForDay(eventDate, pageSize, pageNum);

        pageSize = 1;
        pageNum = -1;
        bookingFacade.getEventsForDay(eventDate, pageSize, pageNum);

        pageSize = -1;
        pageNum = 1;
        bookingFacade.getEventsForDay(eventDate, pageSize, pageNum);

        pageSize = 1;
        pageNum = 0;
        bookingFacade.getEventsForDay(eventDate, pageSize, pageNum);

        pageSize = 0;
        pageNum = 1;
        bookingFacade.getEventsForDay(eventDate, pageSize, pageNum);

        pageSize = 0;
        pageNum = 1;
        bookingFacade.getEventsForDay(null, pageSize, pageNum);
    }

    @Test
    public void testCreateEvent() throws ParseException {
        String date = "2016-05-01";
        Date eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        event = new EventImpl("golf", eventDate, BigDecimal.TEN);
        assertNotNull(event);

        Event createdEvent = bookingFacade.createEvent(event);
        assertNotNull(createdEvent);
        // TODO: 26.06.16 ask about date equals
        assertTrue(event.getDate().compareTo(createdEvent.getDate()) == 0);
        assertSame(event.getTitle(), createdEvent.getTitle());
        assertSame(event.getTicketPrice(), createdEvent.getTicketPrice());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateEventWithWrongParam() throws Exception {
        // TODO: 26.06.16  problem that event id is not null
//        String date = "2016-05-01";
//        Date eventDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        event = new EventImpl(null, null, null);
        bookingFacade.createEvent(event);
    }

    @Test
    public void testUpdateEvent() {
        String newTitle = "box";
        Date newDate = new Date(1466935320000L);

        Event receivedEvent = bookingFacade.getEventById(1L);
        receivedEvent.setTitle(newTitle);
        receivedEvent.setDate(newDate);
        receivedEvent.setTicketPrice(BigDecimal.ONE);

        Event updatedEvent = bookingFacade.updateEvent(receivedEvent);
        assertEquals(newTitle, updatedEvent.getTitle());
        assertEquals(newDate, updatedEvent.getDate());
        assertEquals(BigDecimal.ONE, updatedEvent.getTicketPrice());
    }

    @Test
    public void testDeleteEvent() throws Exception {

    }

    @Test
    public void testGetUserById() throws Exception {

    }

    @Test
    public void testGetUserByEmail() throws Exception {

    }

    @Test
    public void testGetUsersByName() throws Exception {

    }

    @Test
    public void testCreateUser() throws Exception {

    }

    @Test
    public void testUpdateUser() throws Exception {

    }

    @Test
    public void testDeleteUser() throws Exception {

    }

    @Test
    public void testBookTicket() throws Exception {

    }

    @Test
    public void testGetBookedTickets() throws Exception {

    }

    @Test
    public void testCancelTicket() throws Exception {

    }

    @Test
    public void testCreateUserAccount() throws Exception {

    }

    @Test
    public void testGetUserAccountById() throws Exception {

    }

    @Test
    public void testGetUserAccountByUserId() throws Exception {

    }

    @Test
    public void testUpdateUserAccount() throws Exception {

    }

    @Test
    public void testDeleteUserAccount() throws Exception {

    }

    @Test
    public void testRechargeAccountByAccountId() throws Exception {

    }

    @Test
    public void testRechargeAccountByUserId() throws Exception {

    }
}