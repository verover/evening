package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.dto.EventDTO;
import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.payload.request.EventRequest;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/events")
@Validated
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    EventDetailService eventDetailService;

    @PostMapping
    public ResponseEntity<WebResponse<Event>> createEvent(@Valid @RequestBody EventRequest eventRequest){
        Event event = eventService.save(eventRequest) ;
        return ResponseEntity.status(HttpStatus.OK)
                .body(new WebResponse<>("Successfully add new event",event));
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Event>>> listEventWithPage(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "topics", required = false) String topics,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate
    ) {
        Pageable pageable = PageRequest.of(page, size);
        EventDTO eventDTO = new EventDTO(name,topics,startDate,endDate);
        Page<Event> events = this.eventService.listWithPage(pageable,eventDTO);
        PageResponse<Event> pageResponse = new PageResponse<>(
                events.getContent(),
                events.getTotalElements(),
                events.getTotalPages(),
                page,
                size
        );
        String msg = "";
        if(events.getContent().isEmpty()){
            msg = "Event is not available";
        }else {
            msg = "Successfuly get data event";
        }
        WebResponse<PageResponse<Event>> response = new WebResponse<>(msg, pageResponse);
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

    @PutMapping(value = "/{id}")
    public ResponseEntity<WebResponse<Event>> udpateEventById(@PathVariable("id") String id,@RequestBody EventRequest eventRequest){
         Event event = eventService.updateById(id,eventRequest);
         return ResponseEntity.status(HttpStatus.OK)
                 .body(new WebResponse<>("Successfully update event",event));
    }


    //EventDetails
    //add more eventDetail at the update event by id

    @PutMapping(value = "/eventdetails/update/{id}")
    public ResponseEntity<WebResponse<EventDetail>> updateEventDetailById(@PathVariable("id") String id,@RequestBody EventDetail eventDetail){
        EventDetail saveEventDetail = eventDetailService.updateById(id, eventDetail);
        return ResponseEntity.status(HttpStatus.OK).body(new WebResponse<>("Successfuly update Event Detail",saveEventDetail));
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