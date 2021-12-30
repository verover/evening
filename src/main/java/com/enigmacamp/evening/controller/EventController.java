package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.dto.EventDTO;
import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.payload.EventRequest;
import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.service.EventDetailService;
import com.enigmacamp.evening.service.EventService;
import com.enigmacamp.evening.util.PageResponse;
import com.enigmacamp.evening.util.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    EventDetailService eventDetailService;

    @PostMapping
    public Event createEvent(@RequestBody EventRequest eventRequest){
        return eventService.save(eventRequest);
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Event>>> listEventWithPage(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> events = this.eventService.listWithPage(pageable);
        PageResponse<Event> pageResponse = new PageResponse<>(
                events.getContent(),
                events.getTotalElements(),
                events.getTotalPages(),
                page,
                size
        );
        WebResponse<PageResponse<Event>> response = new WebResponse<>("Successfully get data Event", pageResponse);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(value = "/{eventId}")
    public Event getById(@PathVariable("eventId") String id){
        return eventService.getById(id);
    }

    @DeleteMapping(value = "/{eventId}")
    public ResponseEntity<WebResponse<String>> deleteEventById(@PathVariable("eventId") String id) {
        String message = this.eventService.deleteById(id);
        WebResponse<String> webResponse = new WebResponse<>(message, id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(webResponse);
    }

    @GetMapping("/search/topics/{name}")
    public ResponseEntity<WebResponse<PageResponse<Event>>>  getEventByTopicsName(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @PathVariable("name") String name
    ){
        Pageable  pageable = PageRequest.of(page,size);
        Page<Event> event = this.eventService.findByTopics(pageable,"%" + name +"%");

        PageResponse<Event> pageResponse = new PageResponse<>(
                event.getContent(),
                event.getTotalElements(),
                event.getTotalPages(),
                page,
                size
        );

        WebResponse<PageResponse<Event>> response = new WebResponse<>("Successfully get data Event by Topics", pageResponse);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<WebResponse<PageResponse<Event>>>  getEventByName(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @PathVariable("name") String name
    ){
        Pageable  pageable = PageRequest.of(page,size);
        Page<Event> event = this.eventService.findByName(pageable,"%" + name +"%");
        PageResponse<Event> pageResponse = new PageResponse<>(
                event.getContent(),
                event.getTotalElements(),
                event.getTotalPages(),
                page,
                size
        );
        WebResponse<PageResponse<Event>> response = new WebResponse<>("Successfully get Events by Name", pageResponse);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping(value = "/{id}")
    public Event udpateEventById(@PathVariable("id") String id,@RequestBody EventRequest eventRequest){
        return eventService.updateById(id,eventRequest);
    }


    //EventDetails
    //add more eventDetail at the update event by id

    @PutMapping(value = "/eventdetails/update/{id}")
    public EventDetail updateEventDetailById(@PathVariable("id") String id,@RequestBody EventDetail eventDetail){
        return eventDetailService.updateById(id,eventDetail);
    }

    @GetMapping(value = "/eventdetails/{id}")
    public List<EventDetail> findEventDetailsByEventId(@PathVariable("id")String id){
        return eventDetailService.findByEventId(id);
    }

    @GetMapping(value = "/eventdetail/{id}")
    public EventDetail findEventDetailById(@PathVariable("id")String id){
        return eventDetailService.getById(id);
    }

}
