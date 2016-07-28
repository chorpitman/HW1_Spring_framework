package com.epam.controller;

import com.epam.facade.BookingFacade;
import com.epam.model.Event;
import com.epam.model.impl.EventImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@RequestMapping(value = {"/event"}/*, consumes ="application/json"*/)
@Controller
public class EventController {
    private static Logger log = Logger.getLogger(EventController.class.getName());

    @Autowired
    BookingFacade facade;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody //Аннотация @ResponseBody
    // сообщает фреймворку Spring, что возвращаемый
    // объект следует отправить клиенту как ресурс, преобразовав
    // его в некоторый формат, доступный для клиента.
    // ресурс должен быть преобразован в формат в соответствии с содержимым
    // заголовка Accept. Если в запросе отсутствует заголовок Accept,
    // тогда предполагается, что клиент способен принимать ресурсы в любом формате.
    public Event getEventById(@PathVariable(value = "id") long id) {
        log.info("[getEventById] : " + id);
        return facade.getEventById(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Event createEvent(@RequestBody EventImpl event) {
        log.info("[createEvent] : " + event);
        return facade.createEvent(event);
        // для преобразования данных HTTP-запроса в Java- объекты, передаваемые
        // методам-обработчикам контроллера, можно воспользоваться
        // новой аннотацией @RequestBody
    }

    @RequestMapping(value = "/title", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEventsByTitle(@RequestParam String title,
                                        @RequestParam(value = "pageSize") int pageSize,
                                        @RequestParam("pageNum") int pageNum) {
        log.info("[getEventsByTitle] : " + title + ";" + pageSize + ";" + pageNum);
        return facade.getEventsByTitle(title, pageSize, pageNum);
    }

    @RequestMapping(value = "/day", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEventsForDay(@RequestParam("day") Date day,
                                       @RequestParam("pageSize") int pageSize,
                                       @RequestParam("pageNum") int pageNum) {
        log.info("[getEventsForDay] : " + day + ";" + pageSize + ";" + pageNum);
        return facade.getEventsForDay(day, pageSize, pageNum);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public Event updateEvent(@RequestBody EventImpl event) {
        log.info("[updateEvent] : " + event);
        return facade.updateEvent(event);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteEvent(@PathVariable("id") long eventId) {
        log.info("[deleteEvent] : " + eventId);
        return facade.deleteEvent(eventId);
    }

    @InitBinder //при каждом запросе преобразуется Дата.
    protected void initBinder(WebDataBinder binder) {
        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm"));
    }
}