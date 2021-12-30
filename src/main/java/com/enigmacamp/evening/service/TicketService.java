package com.enigmacamp.evening.service;

import com.enigmacamp.evening.dto.TicketDTO;
import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.payload.request.TicketRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    Ticket create(String eventId,TicketRequest ticket);
    Page<Ticket> readAllTicket(String eventId, Pageable pageable, TicketDTO ticketDTO);
    Ticket readByIdOrThrowNotFound(String eventId, String id);
    Ticket update(String eventId, String id, Ticket ticket);
    void delete(String eventId, String id);
    Ticket getAvailableTicket(String eventId, String id);
}
