package com.enigmacamp.evening.service;

import com.enigmacamp.evening.dto.EventDTO;
import com.enigmacamp.evening.dto.TicketDTO;
import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.payload.request.EventRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EventService {
    Event save(EventRequest eventRequest);
    Event getById(String id);
    String deleteById(String id);
    Page<Event> listWithPage(Pageable pageable, EventDTO eventDTO);
    Page<Event> findBetweenDate(Pageable pageable,String from, String until);
    Event updateById(String id,EventRequest eventRequest);
}