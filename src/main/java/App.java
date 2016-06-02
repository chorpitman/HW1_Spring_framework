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
        System.out.println("Connection exist");
        System.out.println(dataSource.getConnection());
    }
}
