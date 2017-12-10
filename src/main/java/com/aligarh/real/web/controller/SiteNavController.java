package com.aligarh.real.web.controller;

import com.aligarh.real.RequestInterceptor;
import com.aligarh.real.data.EventRepository;
import com.aligarh.real.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class SiteNavController extends RequestInterceptor {
    @Autowired
    private EventRepository eventRepository;

    @RequestMapping("/home")
    public String home(){
        return "sitePages/index";
    }

    @RequestMapping("/hotel")
    public String hotel(){
        return "sitePages/hotel";
    }

    @RequestMapping("/event")
    public String event(){
        return "sitePages/event";
    }

    @RequestMapping("/about")
    public String about(){
        return "sitePages/about";
    }

    @RequestMapping("/honour")
    public String honour(){
        return "sitePages/honour";
    }

    @RequestMapping("/events")
    public String listEvents(ModelMap modelMap){
        List<Event> allEvents = eventRepository.getAllEvents();
        modelMap.put("events",allEvents);
        return "sitePages/events";
    }
}