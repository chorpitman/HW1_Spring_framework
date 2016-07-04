package com.epam;

import com.epam.controller.EventControllerTest;
import com.epam.controller.TicketControllerTest;
import com.epam.controller.UserAccountControllerTest;
import com.epam.controller.UserControllerTest;
import com.epam.dao.EventDaoTest;
import com.epam.dao.TicketDaoTest;
import com.epam.dao.UserAccountDaoTest;
import com.epam.dao.UserDaoTest;
import com.epam.facade.BookingFacadeImplTest;
import com.epam.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserDaoTest.class,
        UserAccountDaoTest.class,
        EventDaoTest.class,
        TicketDaoTest.class,
        UserServiceTest.class,
        UserAccountServiceTest.class,
        UserAccountServiceBLTest.class,
        EventServiceTest.class,
        TicketServiceTest.class,
        TicketServiceTxTest.class,
        BookingFacadeImplTest.class,
        UserControllerTest.class,
        UserAccountControllerTest.class,
        EventControllerTest.class,
        TicketControllerTest.class
})
public class RunAllTests {
}
