package com.epam.controller;

import com.epam.facade.BookingFacade;
import com.epam.model.UserAccount;
import com.epam.model.impl.UserAccountImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/userAccount")
public class UserAccountController {
    private static Logger log = Logger.getLogger(UserAccountController.class.getName());

    @Autowired
    BookingFacade facade;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public UserAccount createUserAccount(@RequestBody UserAccountImpl account) {
        log.info("[createUserAccount] : " + account);
        return facade.createUserAccount(account);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UserAccount getUserAccountById(@RequestParam(value = "id") long accountId) {
        log.info("[getUserAccountById] : " + accountId);
        return facade.getUserAccountById(accountId);
    }

    @RequestMapping(value = "/getUserAccountByUserId/{id}", method = RequestMethod.GET)
    @ResponseBody
    public UserAccount getUserAccountByUserId(@RequestParam(value = "id") long userAccountId) {
        log.info("[getUserAccountByUserId] : " + userAccountId);
        return facade.getUserAccountByUserId(userAccountId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public UserAccount updateUserAccount(@RequestBody UserAccountImpl account) {
        log.info("[updateUserAccount] : " + account);
        return facade.updateUserAccount(account);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteUserAccount(@RequestParam(value = "id") long accountId) {
        log.info("[deleteUserAccount] : " + accountId);
        return facade.deleteUserAccount(accountId);
    }

    @RequestMapping(value = "/rechargeAccountByAccountId/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void rechargeAccountByAccountId(@RequestParam(value = "id") long accountId,
                                           @RequestParam(value = "amount") BigDecimal amount) {
        log.info("[rechargeAccountByAccountId] : accountId" + accountId + "; " + amount);
        facade.rechargeAccountByAccountId(accountId, amount);
    }

    @RequestMapping(value = "/rechargeAccountByUserId/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void rechargeAccountByUserId(@RequestParam(value = "id") long userId,
                                        @RequestParam(value = "amount") BigDecimal amount) {
        log.info("[rechargeAccountByUserId] : userId " + userId + "; " + amount);
        facade.rechargeAccountByUserId(userId, amount);
    }
}
