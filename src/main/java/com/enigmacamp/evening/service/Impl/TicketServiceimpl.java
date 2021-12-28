package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.repository.TicketRepository;
import com.enigmacamp.evening.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceimpl implements TicketService{

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public Ticket create(Ticket ticket) {
        return ticketRepository.save(ticket);
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
