package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.repository.EventDetailRepository;
import com.enigmacamp.evening.service.EventDetailService;
import com.enigmacamp.evening.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EventDetailServiceImpl implements EventDetailService{

    @Autowired
    EventDetailRepository eventDetailRepository;

    @Autowired
    EventService  eventService;

    @Override
    public EventDetail save(EventDetail eventDetail) {
        Event event = eventService.getById(eventDetail.getEvent().getEventId());
        eventDetail.setEvent(event);
        event.getEventDetails().add(eventDetail);
        EventDetail save = eventDetailRepository.save(eventDetail);
        return save;
    }

    @Override
    public List<EventDetail> findAll() {
        return eventDetailRepository.findAll();
    }

    @Override
    public EventDetail getById(String id) {
        return this.findByOrThrowNotFound(id);
    }

    @Override
    public EventDetail updateById(String id, EventDetail eventDetail) {
        eventDetail.setEventDetailId(id);
        return eventDetailRepository.save(eventDetail);
    }

    public EventDetail findByOrThrowNotFound(String id) {
        Optional<EventDetail> eventDetail = this.eventDetailRepository.findById(id);
        if (!eventDetail.isPresent()) {
            throw new NotFoundException("EventDetail is not found");
        }
        return eventDetail.get();
    }

}
