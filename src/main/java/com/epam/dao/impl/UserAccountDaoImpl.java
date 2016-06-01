package com.epam.dao.impl;

import com.epam.dao.UserAccountDao;
import org.apache.log4j.Logger;

import java.math.BigDecimal;

/**
 * Created by Oleg_Chorpita on 6/1/2016.
 */
public class UserAccountDaoImpl implements UserAccountDao {

    private static Logger log = Logger.getLogger(EventDaoImpl.class.getName());

    @Override
    public void rechargeAccount(long userId, BigDecimal amount) {
        log.debug("rechargeAccount: " + userId + " + " + amount);
    }
}
