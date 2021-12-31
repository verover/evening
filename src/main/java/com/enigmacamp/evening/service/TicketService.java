package com.enigmacamp.evening.service;

import com.enigmacamp.evening.dto.TicketDTO;
import com.enigmacamp.evening.entity.Ticket;

import com.enigmacamp.evening.payload.request.TicketRequest;
import com.enigmacamp.evening.payload.response.TicketResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {
    Ticket create(String eventId, TicketRequest ticket);
    Page<Ticket> readAllTicket(String eventId, Pageable pageable, TicketDTO ticketDTO);
    Ticket readByIdOrThrowNotFound(String eventId, String id);
    Ticket update(String eventId, String id, Ticket ticket);
    void delete(String eventId, String id);
    Ticket getAvailableTicket(String eventId, String id);

    TicketResponse convertTicket(Ticket ticket);
    List<TicketResponse> convertAllTicket(List<Ticket> tickets);
}
