package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.dto.EventDTO;
import com.enigmacamp.evening.payload.EventRequest;
import com.enigmacamp.evening.entity.Event;
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
    public ResponseEntity<WebResponse<PageResponse<Event>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "topics", required = false) String topics

    ) {
        Pageable pageable = PageRequest.of(page, size);
        EventDTO eventDTO = new EventDTO(name,topics);

        Page<Event> events = this.eventService.listWithPage(pageable, eventDTO);

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

    @DeleteMapping({"/{eventId}"})
    public ResponseEntity<WebResponse<String>> deleteCustomerById(@PathVariable("eventId") String id) {
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
}
