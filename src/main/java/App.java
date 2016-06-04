import com.epam.dao.UserDao;
import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");

        if (context.getBean("dataSource") == null) {
            System.out.println("bean do not exist");
        } else {
            System.out.println("bean exist");
        }
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        System.out.println("Connection exist: " + dataSource.getConnection());

        UserDao userDao = context.getBean("userDao", UserDao.class);

        //UPDATE is WORK
//        User user = userDao.getUserById(1);
//        user.setName("kuma");
//        user.setEmail("kuma@i.ua");
//        System.out.println(user);
//        userDao.update(user);

        //DELETE is WORK
//        userDao.deleteUser(1);

        //CREATE
//        User user = new UserImpl();
//        user.setId(10);
//        user.setName("bianchi");
//        user.setEmail("bianchi@i.ua");
//        System.out.println(user);
//        System.out.println(userDao.createUser(user));

//        GET BY ID is WORK
//        System.out.println(userDao.getUserById(2));

//        GET BY EMAIL is WORK
//        System.out.println(userDao.getUserByEmail("bianchi@i.ua"));

//        PAGINATION
//        System.out.println(userDao.getUsersByName("bianchi", 1, 1));
    }
}
