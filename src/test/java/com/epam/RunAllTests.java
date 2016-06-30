package com.epam;

import com.epam.dao.EventDaoTest;
import com.epam.dao.TicketDaoTest;
import com.epam.dao.UserAccountDaoTest;
import com.epam.dao.UserDaoTest;
import com.epam.facade.BookingFacadeImplTest;
import com.epam.service.EventServiceTest;
import com.epam.service.TicketServiceTest;
import com.epam.service.UserAccountServiceImplTest;
import com.epam.service.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserDaoTest.class,
        UserAccountDaoTest.class,
        EventDaoTest.class,
        TicketDaoTest.class,
        UserServiceTest.class,
        EventServiceTest.class,
        TicketServiceTest.class,
        UserAccountServiceImplTest.class,
        BookingFacadeImplTest.class,
})
public class RunAllTests {
}
