package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.repository.EventDetailRepository;
import com.enigmacamp.evening.service.EventDetailService;
import com.enigmacamp.evening.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        return eventDetailRepository.save(eventDetail);
    }

    @Override
    public EventDetail getById(String id) {
        return this.findByOrThrowNotFound(id);
    }

    @Override
    public EventDetail updateById(String id, EventDetail eventDetail) {
        EventDetail defEventDetail = this.getById(id);
        eventDetail.setEventDetailId(id);
        eventDetail.setEvent(defEventDetail.getEvent());
        return eventDetailRepository.save(eventDetail);
    }

    public EventDetail findByOrThrowNotFound(String id) {
        Optional<EventDetail> eventDetail = this.eventDetailRepository.findById(id);
        if (eventDetail.isPresent() && eventDetail.get().getIsDeleted() == false) {
            return eventDetail.get();
        }
        throw new NotFoundException("EventDetail is not found");
    }

    public List<EventDetail> findByEventId(String id){
        Event event = eventService.getById(id);
        List<EventDetail> eventDetails = event.getEventDetails();
        List<EventDetail> eventDetailList = new ArrayList<>();
        for (EventDetail eventDetail: eventDetails) {
            if (!eventDetail.getIsDeleted())
            eventDetailList.add(eventDetail);
        }
            return  eventDetailList;
    }

    @Override
    public String deleteById(String id) {
        EventDetail eventDetail = this.findByOrThrowNotFound(id);
        if(eventDetail.getIsDeleted()){
            throw new NotFoundException("Event Not Found");
        }
        eventDetail.setIsDeleted(true);
        eventDetailRepository.save(eventDetail);
        return "Event has been removed";
    }

}
