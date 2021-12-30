package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.service.EventDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(value = "/eventdetails")
public class EventDetailController {

    @Autowired
    EventDetailService eventDetailService;

    @PostMapping
    public EventDetail save(@RequestBody EventDetail eventDetail){
       return eventDetailService.save(eventDetail);
    }

    @GetMapping
    public List<EventDetail> findAll(){
        return eventDetailService.findAll();
    }

    @GetMapping(value = "/{id}")
    public EventDetail getById(@PathVariable("id") String id){
        return eventDetailService.getById(id);
    }

    @PutMapping(value = "/{id}")
    public EventDetail update(@PathVariable("id") String id, EventDetail eventDetail){
        return eventDetailService.updateById(id,eventDetail);
    }
}
