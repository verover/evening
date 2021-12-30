package com.enigmacamp.evening.service;

import com.enigmacamp.evening.dto.EventDTO;
import com.enigmacamp.evening.payload.EventRequest;
import com.enigmacamp.evening.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    Event save(EventRequest eventRequest);
    List<Event> findAll();
    Event getById(String id);
    String deleteById(String id);
    Page<Event> listWithPage(Pageable pageable, EventDTO eventDTO);
    Page<Event> findByTopics(Pageable pageable,String nameTopics);
}
