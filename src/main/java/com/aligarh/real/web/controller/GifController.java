package com.aligarh.real.web.controller;

import com.aligarh.real.RequestInterceptor;
import com.aligarh.real.data.EventRepository;
import com.aligarh.real.data.GifRepository;
import com.aligarh.real.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class GifController extends RequestInterceptor {
    @Autowired
    private GifRepository gifRepository;

    @Autowired
    private EventRepository eventRepository;

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

    /*@RequestMapping("/image")
    public String listGifs(ModelMap modelMap) {
        List<Gif> allGifs = gifRepository.getAllGifs();
        modelMap.put("gifs",allGifs);
        return "home";
    }

    @RequestMapping("/gif/{name}")
    public String gifDetails(@PathVariable String name, ModelMap modelMap) {
        Gif gif = gifRepository.findByName(name);
        modelMap.put("gif",gif);
        return "gif-details";
    }*/

    @RequestMapping("/events")
    public String listEvents(ModelMap modelMap){
        List<Event> allEvents = eventRepository.getAllEvents();
        modelMap.put("events",allEvents);
        return "sitePages/events";

    }
}
