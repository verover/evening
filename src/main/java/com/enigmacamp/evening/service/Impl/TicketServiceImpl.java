package com.enigmacamp.evening.service.Impl;

import java.util.*;

import javax.transaction.Transactional;

import com.enigmacamp.evening.dto.TicketDTO;
import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.entity.TicketDetail;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.payload.request.TicketRequest;
import com.enigmacamp.evening.payload.response.TicketResponse;
import com.enigmacamp.evening.repository.TicketRepository;
import com.enigmacamp.evening.service.EventDetailService;
import com.enigmacamp.evening.service.EventService;
import com.enigmacamp.evening.service.TicketDetailService;
import com.enigmacamp.evening.service.TicketService;
import com.enigmacamp.evening.specification.TicketSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TicketServiceImpl implements TicketService{

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketDetailService ticketDetailService;

    @Autowired
    EventService eventService;

    @Autowired
    EventDetailService eventDetailService;

    @Override
    public Ticket create(String eventId, TicketRequest ticket) {
        Ticket newTicket = ticketRepository.save(new Ticket(eventService.getById(eventId), ticket.getTitle(),
                ticket.getDescription(), ticket.getPrice(), ticket.getStock(),
                ticket.getMinAmmount(), ticket.getMaxAmmount()));
        Set<TicketDetail> ticketDetails = new HashSet<>();
        for(String eventDetail: ticket.getTicketDetail()){
            TicketDetail ticketDetail = new TicketDetail(newTicket, eventDetailService.getById(eventDetail));
            ticketDetails.add(ticketDetail);
            ticketDetailService.create(ticketDetail);
        }
        newTicket.setTicketDetail(ticketDetails);
        return ticketRepository.save(newTicket);
    }

    @Override
    public Ticket readByIdOrThrowNotFound(String eventId, String id) {
        eventService.getById(eventId);
        Optional<Ticket> ticket = ticketRepository.getActiveTicket(id);
        if(ticket.isPresent()){
            return ticket.get();
        }else{
            throw new NotFoundException("Sorry, ticket that you looking for is unavailable!");
        }
    }


    @Override
    public Ticket update(String eventId, String id, Ticket ticket) {
        try{
            Ticket defaultTicket = readByIdOrThrowNotFound(eventId, id);
            // Ticket updateTicket = ticketRepository.save(ticket);
            // for(TicketDetail ticketDetail : updateTicket.getTicketDetails()){
            //     //checking if the corresponding tickets is already on the list
            //     //check the validation
            //     //if the details are not on the list just create new one
            // }
            return defaultTicket;
        }catch(Exception ex){
            throw new NotFoundException("Sorry, ticket update is failed!");
        }
    }

    @Override
    public void delete(String eventId, String id) {
        Ticket ticket = readByIdOrThrowNotFound(eventId, id);
        if(ticket.getIsDeleted()){
            throw new NotFoundException("Sorry, ticket that you looking for is unavailable!");
        }
        ticket.setIsDeleted(true);
        ticketRepository.save(ticket);
    }

    @Override
    public Ticket getAvailableTicket(String eventId, String id) {
         return ticketRepository.getActiveTicket(id).orElseThrow(() -> new NotFoundException("Sorry, ticket that you looking for is unavailable!"));
    }

    @Override
    public Page<Ticket> readAllTicket(String eventId, Pageable pageable, TicketDTO ticketDTO) {
        eventService.getById(eventId);
        Specification<Ticket> specification = TicketSpecification.getSpecification(ticketDTO);
        return ticketRepository.findAll(specification, pageable);
    }

    @Override
    public TicketResponse convertTicket(Ticket ticket) {
        return new TicketResponse(ticket.getId(), ticket.getEvent().getName(), ticket.getTitle(), ticket.getDescription(),
                ticket.getPrice(), ticket.getStock(), ticket.getMinAmmount(), ticket.getMaxAmmount(),
                ticketDetailService.readByTicketId(ticket.getTicketDetail()));
    }

    @Override
    public List<TicketResponse> convertAllTicket(List<Ticket> tickets) {
        List<TicketResponse> responses = new ArrayList<>();
        for (Ticket ticket: tickets) {
            responses.add(convertTicket(ticket));
        }
        return responses;
    }
}