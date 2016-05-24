package com.epam.service.all;

import com.epam.service.EventServiceTest;
import com.epam.service.TicketServiceTest;
import com.epam.service.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Oleg_Chorpita on 5/24/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceTest.class,
        EventServiceTest.class,
        TicketServiceTest.class
})


public class TesAllServices {
}
