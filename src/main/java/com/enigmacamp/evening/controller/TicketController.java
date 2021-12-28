package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.service.TicketService;

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
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket){
        Ticket newTicket = ticketService.create(ticket);
        System.out.println(ticket);
        return new ResponseEntity<Ticket>(newTicket, HttpStatus.CREATED);
    }

    @GetMapping
    public String getTicket(){
        return "TEST!";
    }
}
