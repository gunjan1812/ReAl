package com.teamtreehouse.giflib.controller;

import com.teamtreehouse.giflib.data.EventRepository;
import com.teamtreehouse.giflib.data.GifRepository;
import com.teamtreehouse.giflib.model.Event;
import com.teamtreehouse.giflib.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
public class GifController {
    @Autowired
    private GifRepository gifRepository;

    @Autowired
    private EventRepository eventRepository;


//    @RequestMapping("/")
//    public String home(){
//        return "index";
//    }

    @RequestMapping("/hotel")
    public String hotel(){
        return "hotel";

    }

    @RequestMapping("/event")
    public String event(){
        return "event";

    }

    @RequestMapping("/about")
    public String about(){
        return "about";

    }

    @RequestMapping("/honour")
    public String honour(){
        return "honour";
    }

    @RequestMapping("/image")
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
    }

    @RequestMapping("/events")
    public String listEvents(ModelMap modelMap){
        List<Event> allEvents = eventRepository.getAllEvents();
        modelMap.put("events",allEvents);
        return "events";

    }
}
