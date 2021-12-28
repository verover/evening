package com.enigmacamp.evening.service.Impl;

import javax.transaction.Transactional;

import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.entity.TicketDetail;
import com.enigmacamp.evening.repository.TicketRepository;
import com.enigmacamp.evening.service.TicketDetailService;
import com.enigmacamp.evening.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TicketServiceImpl implements TicketService{

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketDetailService ticketDetailService;

    @Override
    public Ticket create(Ticket ticket) {
        Ticket newTicket = ticketRepository.save(ticket);
        for(TicketDetail ticketDetail : newTicket.getTicketDetails()){
            // TODO: Check event detail is valid with corresponding event.
            // TODO: Check if event detail only allowed once.
            // EventDetail eventDetail = eventService.readById(ticketDetail.getEvent().getId());
            // If event detail pass all validations:
            ticketDetail.setTicket(ticket);
            ticketDetailService.create(ticketDetail);
        }
        return newTicket;
    }

    @Override
    public Ticket readById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Ticket update(String id, Ticket ticket) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        
    }
    
}
