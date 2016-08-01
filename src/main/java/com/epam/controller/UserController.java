package com.epam.controller;

import com.epam.facade.BookingFacade;
import com.epam.model.User;
import com.epam.model.impl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/user")
@Controller
public class UserController {
    private static Logger log = Logger.getLogger(UserController.class.getName());


    @Autowired
    BookingFacade facade;

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUserById(@PathVariable("id") long userId) {
        log.info("[getUserById] : " + userId);
        return facade.getUserById(userId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public User createUser(@RequestBody UserImpl user) {
        log.info("[createUser] : " + user);
        return facade.createUser(user);
    }

    @RequestMapping(value = "/name", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsersByName(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "pageSize", defaultValue = "1") int pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        log.info("[getUsersByName] : " + "userName " + name + ";" + pageSize + ";" + pageNum);
        return facade.getUsersByName(name, pageSize, pageNum);
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    @ResponseBody
    public User getUserByEmail(@PathVariable("email") String email) {
        log.info("[getUserByEmail] : " + email);
        return facade.getUserByEmail(email);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public User updateUser(@RequestBody UserImpl user) {
        log.info("[updateUser] : " + user);
        return facade.updateUser(user);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteUser(@PathVariable("id") long userId) {
        log.info("[deleteUser] : " + userId + " userId");
        return facade.deleteUser(userId);
    }
}