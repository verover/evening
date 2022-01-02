package com.enigmacamp.evening.service.Impl;

import java.util.*;

import javax.transaction.Transactional;

import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.entity.TicketDetail;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.payload.response.TicketDetailResponse;
import com.enigmacamp.evening.repository.TicketDetailRepository;
import com.enigmacamp.evening.service.EventDetailService;
import com.enigmacamp.evening.service.TicketDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TicketDetailServiceImpl implements TicketDetailService {

    @Autowired
    TicketDetailRepository ticketDetailRepository;

    @Autowired
    EventDetailService eventDetailService;

    @Override
    public TicketDetail create(TicketDetail ticketDetail) {
        Event event = ticketDetail.getEventDetail().getEvent();
        Event choosen = eventDetailService.getById(ticketDetail.getEventDetail().getEventDetailId()).getEvent();
        if(choosen.equals(event)){
            return ticketDetailRepository.save(ticketDetail);
        }else{
            throw new InputMismatchException("Sorry, event not synchronized");
        }
    }

    @Override
    public TicketDetail readByIdOrThrowNotFound(String id) {
        Optional<TicketDetail> detail = ticketDetailRepository.findById(id);
        if(detail.isPresent()){
            return detail.get();
        }else{
            throw new NotFoundException("Sorry, ticket that you looking for is unavailable!");
        }
    }

    @Override
    public TicketDetail update(String id, TicketDetail ticketDetail) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TicketDetailResponse> readByTicketId(Set<TicketDetail> ticket) {
        List<TicketDetailResponse> ticketDetails = new ArrayList<>();
        for (TicketDetail detail:ticket) {
            ticketDetails.add(new TicketDetailResponse(detail.getId(), detail.getEventDetail().getEventDetailId(),
                    detail.getEventDetail().getDate().toString(), detail.getEventDetail().getLocation()));
        }
        return ticketDetails;
    }

    @Override
    public void delete(String id) {
        TicketDetail ticketDetail = readByIdOrThrowNotFound(id);
        ticketDetailRepository.delete(ticketDetail);
    }
}
