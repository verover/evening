package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.dto.EventRequest;
import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping
    public Event createEvent(@RequestBody EventRequest eventRequest){
        return eventService.save(eventRequest);
    }

    @GetMapping
    public List<Event> findAll(){
        return eventService.findAll();
    }

    @GetMapping(value = "/{eventId}")
    public Event getById(@PathVariable("eventId") String id){
        return eventService.getById(id);
    }
}
