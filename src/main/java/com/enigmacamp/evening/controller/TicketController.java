package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.dto.TicketDTO;
import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.payload.request.TicketRequest;
import com.enigmacamp.evening.service.TicketService;
import com.enigmacamp.evening.util.PageResponse;
import com.enigmacamp.evening.util.WebResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import antlr.debug.Event;

@RestController
@RequestMapping("/events/{id}/tickets")
public class TicketController{
    @Autowired
    TicketService ticketService;

    @PostMapping
    public ResponseEntity<WebResponse<TicketRequest>> createTicket(@PathVariable(name = "id", required = true) String eventId,
        @RequestBody TicketRequest ticket){
        ticketService.create(eventId, ticket);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new WebResponse<>(String.format("Successfully Create New Ticket (%s)", ticket.getTitle()), ticket));
    }

    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<Ticket>>> getTicket(
        @PathVariable(name = "id", required = true) String eventId,
        @RequestParam(name = "size", defaultValue = "10") Integer size,
        @RequestParam(name = "page", defaultValue = "0") Integer page,
        @RequestParam(name = "name", required = false) String ticketName,
        @RequestParam(name = "availability", required = false) Boolean ticketAvailable){
        Pageable pageable = PageRequest.of(page, size);
        TicketDTO ticketDTO = new TicketDTO(ticketName, ticketAvailable);
        Page<Ticket> tickets = ticketService.readAllTicket(eventId, pageable, ticketDTO); 
        PageResponse<Ticket> pageResponse = new PageResponse<>(
            tickets.getContent(), 
            tickets.getTotalElements(), 
            tickets.getTotalPages(), page, size); 
        return ResponseEntity.status(HttpStatus.OK)
                .body(new WebResponse<>("Successfully Load all tickets", pageResponse));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<WebResponse<Ticket>> getTicketId(@PathVariable(name = "id", required = true) String eventId, 
        @PathVariable("ticketId") String id){
        Ticket ticket = ticketService.readByIdOrThrowNotFound(eventId, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new WebResponse<>(String.format("Ticket Id of %s is available", id), ticket));
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<WebResponse<Ticket>> deleteTicket(@PathVariable(name = "id", required = true) String eventId, 
        @PathVariable("ticketId") String id){
        Ticket ticket = ticketService.readByIdOrThrowNotFound(eventId, id);
        ticketService.delete(eventId, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new WebResponse<>(String.format("Ticket Id of %s is deleted", id), ticket));
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<WebResponse<Ticket>> updateTicket(@PathVariable(name = "id", required = true) String eventId, 
    @PathVariable(name = "ticketId") String id, @RequestBody Ticket ticket){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new WebResponse<>(String.format("Successfully Update New Ticket (%s)", ticket.getId()), ticketService.update(eventId, id, ticket)));
    }
}
