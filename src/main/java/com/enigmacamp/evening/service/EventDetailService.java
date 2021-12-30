package com.enigmacamp.evening.service;

import com.enigmacamp.evening.entity.EventDetail;

import java.util.List;

public interface EventDetailService {
    EventDetail create(EventDetail eventDetail);
    List<EventDetail> findAll();
    EventDetail getById(String id);
    EventDetail updateById(String id,EventDetail eventDetail);
    List<EventDetail> findByEventId(String id);
}
