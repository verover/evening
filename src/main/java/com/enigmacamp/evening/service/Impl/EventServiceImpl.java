package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.payload.request.EventRequest;
import com.enigmacamp.evening.entity.Category;
import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.entity.Topics;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.repository.EventRepository;
import com.enigmacamp.evening.service.CategoryService;
import com.enigmacamp.evening.service.EventDetailService;
import com.enigmacamp.evening.service.EventService;
import com.enigmacamp.evening.service.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    TopicsService topicsService;

    @Autowired
    EventDetailService eventDetailService;

    @Override
    public Event save(EventRequest eventRequest) {
        Category category = categoryService.getById(eventRequest.getCategory());
        Topics topics = topicsService.getById(eventRequest.getTopics());
        Event event = new Event();
        event.setOrganizerId(eventRequest.getOrganizerId());
        event.setName(eventRequest.getName());
        event.setBannerImage(eventRequest.getBannerImage());
        event.setCategory(category);
        event.setTopics(topics);
        event.setEventDetails(eventRequest.getEventDetails());
        for(EventDetail eventDetail : event.getEventDetails()){
            eventDetail.setEvent(event);
            eventDetailService.save(eventDetail);
        }
        Event saveEvent = eventRepository.save(event);
        return saveEvent;
    }

    @Override
    public Event getById(String id) {
        return findByOrThrowNotFound(id);
    }

    @Override
    public String deleteById(String id) {
        Event event = this.findByOrThrowNotFound(id);
        if(event.getIsDeleted()){
            throw new NotFoundException("Event Not Found");
        }
        event.setIsDeleted(true);
        eventRepository.save(event);
        return "Event has been removed";
    }

    @Override
    public Page<Event> listWithPage(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    public Event findByOrThrowNotFound(String id) {
        Optional<Event> event = this.eventRepository.findById(id);
        if (event.isPresent() && event.get().getIsDeleted() == false) {
            return event.get();
        }
        throw new NotFoundException("Event is not found");
    }

    @Override
    public Page<Event> findByTopics(Pageable pageable,String nameTopics){
        return eventRepository.findByNameTopic(nameTopics,pageable);
    }

    @Override
    public Page<Event> findByName(Pageable pageable, String nameEvent) {
        return eventRepository.findByName(nameEvent,pageable);
    }

    @Override
    public Page<Event> findBetweenDate(Pageable pageable,String from, String until) {
//        return eventRepository.findBetweenDate(from,until,pageable);
        return null;
    }

    @Override
    public Event updateById(String id,EventRequest eventRequest) {
        Category category = categoryService.getById(eventRequest.getCategory());
        Topics topics = topicsService.getById(eventRequest.getTopics());
        Event defaultEvent = this.getById(id);
        Event event = new Event();
        event.setEventId(id);
        event.setOrganizerId(eventRequest.getOrganizerId());
        event.setName(eventRequest.getName());
        event.setBannerImage(eventRequest.getBannerImage());
        event.setCategory(category);
        event.setTopics(topics);
        event.setEventDetails(defaultEvent.getEventDetails());
        Event saveEvent = eventRepository.save(event);
        if(eventRequest.getEventDetails() != null) {
            for (EventDetail eventDetail : eventRequest.getEventDetails()) {
                eventDetail.setEvent(saveEvent);
                eventDetailService.save(eventDetail);
                saveEvent.getEventDetails().add(eventDetail);
            }
        }
        return saveEvent;
    }


}
