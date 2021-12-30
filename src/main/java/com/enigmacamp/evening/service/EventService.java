package com.enigmacamp.evening.service;

import com.enigmacamp.evening.payload.EventRequest;
import com.enigmacamp.evening.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EventService {
    Event save(EventRequest eventRequest);
    Event getById(String id);
    String deleteById(String id);
    Page<Event> listWithPage(Pageable pageable);
    Page<Event> findByTopics(Pageable pageable,String nameTopics);
    Page<Event> findByName(Pageable pageable,String nameEvent);
    Page<Event> findBetweenDate(Pageable pageable,String from, String until);
    Event updateById(String id,EventRequest eventRequest);
}
