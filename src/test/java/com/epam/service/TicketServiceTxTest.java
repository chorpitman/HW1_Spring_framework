package com.epam.service;

import com.epam.config.ServiceTestConfig;
import com.epam.model.Ticket;
import com.epam.service.TicketService;
import com.epam.service.UserAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
@ActiveProfiles("test")
@Sql(scripts = {"classpath:sql/drop.sql", "classpath:sql/ddl_in_memory.sql", "classpath:sql/dml_in_memory.sql"})
public class TicketServiceTxTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testTxBookTicketException() {
        TransactionStatus transactionStatus = transactionTemplate.getTransactionManager().getTransaction(transactionTemplate);
        userAccountService.rechargeAccountByUserId(2, new BigDecimal(100));
        try {
            Ticket bookedTicket = ticketService.bookTicket(2, 2, 20, Ticket.Category.BAR);
        } catch (Exception e) {
            assertTrue(transactionStatus.isRollbackOnly());
        }
        transactionTemplate.getTransactionManager().rollback(transactionStatus);
    }

    @Test
    public void testTxBookTicket() {
        TransactionStatus transactionStatus = transactionTemplate.getTransactionManager().getTransaction(transactionTemplate);
        userAccountService.rechargeAccountByUserId(2, new BigDecimal(301));
        try {
            Ticket bookedTicket = ticketService.bookTicket(2, 2, 20, Ticket.Category.BAR);
        } catch (Exception e) {
            assertFalse(transactionStatus.isRollbackOnly());
        } finally {
            transactionTemplate.getTransactionManager().rollback(transactionStatus);
        }
    }
}