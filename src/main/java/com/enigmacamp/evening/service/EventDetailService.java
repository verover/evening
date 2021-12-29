package com.enigmacamp.evening.service;

import com.enigmacamp.evening.entity.EventDetail;

import java.util.List;

public interface EventDetailService {
    EventDetail save(EventDetail eventDetail);
    List<EventDetail> findAll();
    EventDetail getById(String id);
}
