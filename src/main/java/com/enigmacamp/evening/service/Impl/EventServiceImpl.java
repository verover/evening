package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.dto.EventDTO;
import com.enigmacamp.evening.entity.*;
import com.enigmacamp.evening.payload.request.EventRequest;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.repository.EventRepository;
import com.enigmacamp.evening.service.*;
import com.enigmacamp.evening.specification.EventSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    @Autowired
    OrganizerService organizerService;

    @Override
    public Event save(EventRequest eventRequest) {
        Category category = categoryService.getById(eventRequest.getCategoryId());
        Topics topics = topicsService.getById(eventRequest.getTopicsId());
        Organizer organizer = organizerService.getOrganizerById(eventRequest.getOrganizerId());
        Event event = new Event();
        event.setOrganizer(organizer);
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
        Event event = findByOrThrowNotFound(id);
        List<EventDetail> eventDetailList = new ArrayList<>();
        for (EventDetail e : event.getEventDetails()) {
            if(e.getIsDeleted() == false)
                eventDetailList.add(e);
        }
        event.setEventDetails(eventDetailList);
        return event;
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
    public Page<Event> listWithPage(Pageable pageable, EventDTO eventDTO) {
        Specification<Event> specification = EventSpecification.getSpecification(eventDTO);
        return eventRepository.findAll(specification,pageable);
    }


    public Event findByOrThrowNotFound(String id) {
        Optional<Event> event = this.eventRepository.findById(id);
        if (event.isPresent() && event.get().getIsDeleted() == false) {
            return event.get();
        }
        throw new NotFoundException("Event is not found");
    }

    @Override
    public Page<Event> findBetweenDate(Pageable pageable,String from, String until) {
//        return eventRepository.findBetweenDate(from,until,pageable);
        return null;
    }

    @Override
    public Event updateById(String id,EventRequest eventRequest) {
        Category category = categoryService.getById(eventRequest.getCategoryId());
        Topics topics = topicsService.getById(eventRequest.getTopicsId());
        Organizer organizer = organizerService.getOrganizerById(eventRequest.getOrganizerId());
        Event defaultEvent = this.getById(id);
        Event event = new Event();
        event.setEventId(id);
        event.setOrganizer(organizer);
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