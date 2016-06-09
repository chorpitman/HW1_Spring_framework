import com.epam.dao.EventDao;
import com.epam.dao.TicketDao;
import com.epam.dao.UserAccountDao;
import com.epam.dao.UserDao;
import com.epam.dao.impl.TicketDaoImpl;
import com.epam.model.Event;
import com.epam.model.User;
import com.epam.model.impl.EventImpl;
import com.epam.model.impl.UserImpl;
import com.epam.service.UserService;
import com.epam.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");

        if (context.getBean("dbInMemory") == null) {
            System.out.println("bean do not exist");
        } else {
            System.out.println("bean exist");
        }
        DataSource dataSource = context.getBean("dbInMemory", DataSource.class);
        System.out.println("Connection exist: " + dataSource.getConnection());

        UserDao userDao = context.getBean("userDao", UserDao.class);

        //UPDATE is WORK
//        User user = userDao.getUserById(1);
//        user.setName("cube");
//        user.setEmail("cube@i.ua");
//        System.out.println(user);
//        userDao.update(user);

        //DELETE is WORK
//        userDao.deleteUser(1);

        //CREATE
//        UserService user = new UserServiceImpl();
//        user.createUser(new UserImpl("govno", "otstoy"));
//        user.setEmail("bianchi@i.ua");
//        System.out.println(user);
//        System.out.println(userDao.createUser(user));

//        GET BY ID is WORK
        System.out.println(userDao.getUserById(7));

//        GET BY EMAIL is WORK
//        System.out.println(userDao.getUserByEmail("bianchi@i.ua"));

//        GET BY NAME
//        System.out.println(userDao.getUsersByName("John", 1, 5));

//        PAGINATION
//        System.out.println(userDao.getUsersByName("bianchi", 1, 1));

//EVENT
        EventDao eventDao = context.getBean("eventDao", EventDao.class);
//CREATE EVENT
//        Event event = new EventImpl();
//        event.setTitle("Box");
//        event.setDate(new Date());
//        event.setTicketPrice(new BigDecimal("500.00"));
//        eventDao.createEvent(event);

//DELETE EVENT
//        eventDao.deleteEvent(1);

//UPDATE
//        Event event = eventDao.getEventById(2);
//        event.setDate(new Date());
//        event.setTitle("Fight");
//        event.setTicketPrice(new BigDecimal("12"));
//        eventDao.updateEvent(event);
//GET EVENT BY ID
//        System.out.println(eventDao.getEventById(2));
//GET EVENT BY TITLE
//        List<Event> eventList = eventDao.getEventsByTitle("fight", 1, 1);
//        System.out.println(eventList);


        UserAccountDao accountDao = context.getBean("accountDao", UserAccountDao.class);
        //get by Id - READ
//        System.out.println(accountDao.getUserAccountbyId(4));

        //delete
//        accountDao.deleteUserAccount(4);

        //createUserAccount
//        UserAccount userAccount = new UserAccountImpl();
//        userAccount.setUserId(5);
//        userAccount.setAmount(new BigDecimal(5000));
//        accountDao.createUserAccount(userAccount);

        //update
//        UserAccount update = accountDao.getUserAccountbyId(5);
//        update.setUserId(5);
//        update.setAmount(new BigDecimal(100.99));
//        accountDao.updateUserAccount(update);

        TicketDao ticketDao = context.getBean(TicketDaoImpl.class);
//        Ticket ticket = new TicketImpl();
//        ticket.setUserId(1);
//        ticket.setEventId(2);
//        ticket.setPlace(555);
//        ticket.setCategory(Ticket.Category.PREMIUM);

//        System.out.println(ticketDao.bookTicket(7, 1, 798, Ticket.Category.PREMIUM));

        //cancel ticket
//        ticketDao.cancelTicket(14);

        //getBookedTickets
//        User user = new UserImpl();
//        user.setId(7);
//        System.out.println(ticketDao.getBookedTickets(user, 6, 2));

        //getBookedTickets
//        Event event = new EventImpl();
//        event.setId(1);
//        System.out.println(ticketDao.getBookedTickets(event, 6, 3));
    }

}
