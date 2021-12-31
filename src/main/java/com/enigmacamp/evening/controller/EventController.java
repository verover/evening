package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.entity.Category;
import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.payload.EventRequest;
import com.enigmacamp.evening.service.EventDetailService;
import com.enigmacamp.evening.service.EventService;
import com.enigmacamp.evening.util.PageResponse;
import com.enigmacamp.evening.util.RequestResponse;
import com.enigmacamp.evening.util.WebResponse;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/events")
@Validated
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    EventDetailService eventDetailService;

    @PostMapping
    public ResponseEntity<RequestResponse<Event>> createEvent(@Valid @RequestBody EventRequest eventRequest){

            RequestResponse<Event> response = new RequestResponse<>();
            response.getMessages().add("Successfuly Created Event");
            response.setStatus(true);
            response.setData(eventService.save(eventRequest));
            return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<RequestResponse<?>> handleConstraintValidationException(ConstraintViolationException e){
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        RequestResponse<?> response = new RequestResponse<>();
        ConstraintViolationImpl violation = (ConstraintViolationImpl) violations.iterator().next();
        response.setStatus(false);
        String sizeValidation = "{javax.validation.constraints.Size.message}";
        if(violation.getMessageTemplate().equalsIgnoreCase(sizeValidation)){
            response.getMessages().add("Require Size minimun 10 and max 50");
        }
        response.getMessages().add(violation.getMessageTemplate());
        response.getMessages().remove(sizeValidation);
        response.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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
    public ResponseEntity<WebResponse<Event>> getById(@PathVariable("eventId") String id){
        Event event = eventService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>(" Event has been found",event));
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
        String message ="Successfully get data Event by Topics";
        if (event.getContent().isEmpty()){
            message = "Events is not Found";
        }
        WebResponse<PageResponse<Event>> response = new WebResponse<>(message, pageResponse);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }


    @GetMapping("/search/date/{from}/{until}")
    public ResponseEntity<WebResponse<PageResponse<Event>>>  findBetweenDate(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @PathVariable("from") String from,
            @PathVariable("until") String until
    ){
        Pageable  pageable = PageRequest.of(page,size);
        Page<Event> event = this.eventService.findBetweenDate(pageable,from,until);

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
    public ResponseEntity<WebResponse<PageResponse<Event>>>  findEventByName(
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
        String message ="Successfully get data Event by Name";
        if (event.getContent().isEmpty()){
            message = "Events is not Found";
        }
        WebResponse<PageResponse<Event>> response = new WebResponse<>(message, pageResponse);
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

    @DeleteMapping(value = "eventdetail/{eventDetailId}")
    public ResponseEntity<WebResponse<String>> deleteEventDetailById(@PathVariable("eventDetailId") String id) {
        String message = this.eventDetailService.deleteById(id);
        WebResponse<String> webResponse = new WebResponse<>(message, id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(webResponse);
    }

}
