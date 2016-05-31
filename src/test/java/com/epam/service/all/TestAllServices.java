package com.epam.service.all;

import com.epam.service.EventServiceTest;
import com.epam.service.TicketServiceTest;
import com.epam.service.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceTest.class,
        EventServiceTest.class,
        TicketServiceTest.class
})
public class TestAllServices {
}
