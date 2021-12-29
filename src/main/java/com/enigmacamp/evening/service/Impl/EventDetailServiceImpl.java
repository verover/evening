package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.repository.EventDetailRepository;
import com.enigmacamp.evening.service.EventDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventDetailServiceImpl implements EventDetailService{

    @Autowired
    EventDetailRepository eventDetailRepository;

    @Override
    public EventDetail save(EventDetail eventDetail) {
          return eventDetailRepository.save(eventDetail);
    }

    @Override
    public List<EventDetail> findAll() {
        return eventDetailRepository.findAll();
    }
}
