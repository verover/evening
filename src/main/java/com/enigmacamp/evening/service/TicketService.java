package com.enigmacamp.evening.service;

import com.enigmacamp.evening.entity.Ticket;

public interface TicketService {
    Ticket create(Ticket ticket);
    // Page<Ticket> listWithPage(Pageable pageable, TicketDTO ticketDTO);
    Ticket readById(String id);
    Ticket update(String id, Ticket ticket);
    void delete(String id);
}
