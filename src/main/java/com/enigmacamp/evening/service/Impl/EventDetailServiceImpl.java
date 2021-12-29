package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.repository.EventDetailRepository;
import com.enigmacamp.evening.service.EventDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public EventDetail getById(String id) {
        return this.findByOrThrowNotFound(id);
    }

    public EventDetail findByOrThrowNotFound(String id) {
        Optional<EventDetail> eventDetail = this.eventDetailRepository.findById(id);
        if (!eventDetail.isPresent()) {
            throw new NotFoundException("EventDetail is not found");
        }
        return eventDetail.get();
    }

}
