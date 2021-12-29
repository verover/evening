package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.dto.EventRequest;
import com.enigmacamp.evening.entity.Category;
import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.entity.Topics;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.repository.CategoryRepository;
import com.enigmacamp.evening.repository.EventRepository;
import com.enigmacamp.evening.repository.TopicsRepository;
import com.enigmacamp.evening.service.CategoryService;
import com.enigmacamp.evening.service.EventDetailService;
import com.enigmacamp.evening.service.EventService;
import com.enigmacamp.evening.service.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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
        Event saveEvent = eventRepository.save(event);
        for(EventDetail eventDetail : saveEvent.getEventDetails()){
            eventDetail.setEvent(saveEvent);
            eventDetailService.save(eventDetail);
        }
        return saveEvent;
    }


    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event getById(String id) {
        return findByOrThrowNotFound(id);
    }

    @Override
    public void deleteById(String id) {

    }
    public Event findByOrThrowNotFound(String id) {
        Optional<Event> event = this.eventRepository.findById(id);
        if (!event.isPresent()) {
            throw new NotFoundException("Event is not found");
        }
        return event.get();
    }
}
