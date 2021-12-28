package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.service.TicketService;
import com.enigmacamp.evening.util.WebResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PostMapping
    public ResponseEntity<WebResponse<Ticket>> createTicket(@RequestBody Ticket ticket){
        Ticket newTicket = ticketService.create(ticket);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new WebResponse<>(String.format("Successfully Create New Ticket (%s)", newTicket.getId()), newTicket));
    }

    @GetMapping
    public String getTicket(){
        return "TEST!";
    }
}
