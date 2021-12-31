package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.dto.TicketDTO;
import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.payload.request.TicketRequest;
import com.enigmacamp.evening.payload.response.TicketResponse;
import com.enigmacamp.evening.service.TicketService;
import com.enigmacamp.evening.util.PageResponse;
import com.enigmacamp.evening.util.WebResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events/{id}/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;
    
    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<TicketResponse>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @PathVariable(name = "id") String eventId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "availability", defaultValue = "true") Boolean avail
    ) {
        Pageable pageable = PageRequest.of(page, size);
        TicketDTO ticketDTO = new TicketDTO(name, avail);
        Page<Ticket> events = ticketService.readAllTicket(eventId, pageable, ticketDTO);
        PageResponse<TicketResponse> pageResponse = new PageResponse<>(
                ticketService.convertAllTicket(events.getContent()),
                events.getTotalElements(),
                events.getTotalPages(),
                page,
                size
        );
        WebResponse<PageResponse<TicketResponse>> response = new WebResponse("tickets data are loaded successfully",
                pageResponse);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<WebResponse<TicketResponse>> getTickets(
            @PathVariable(name = "id") String eventId,
            @PathVariable(name = "ticketId") String ticketId){
        Ticket ticket = ticketService.readByIdOrThrowNotFound(eventId, ticketId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>("Ticket are loaded successfully", ticketService.convertTicket(ticket)));
    }

    @PostMapping
    public ResponseEntity<WebResponse<TicketResponse>> createNewTicket(@PathVariable(name = "id") String eventId,
        @RequestBody TicketRequest ticketRequest){
        Ticket ticket = ticketService.create(eventId, ticketRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new WebResponse<>("successfully add new tickets", ticketService.convertTicket(ticket)));
    }
}
