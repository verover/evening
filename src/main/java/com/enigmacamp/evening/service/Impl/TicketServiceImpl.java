package com.enigmacamp.evening.service.Impl;

import java.util.*;

import javax.transaction.Transactional;

import com.enigmacamp.evening.dto.TicketDTO;
import com.enigmacamp.evening.entity.Event;
import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.entity.TicketDetail;
import com.enigmacamp.evening.exception.InvalidInputException;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.payload.request.tickedetail.TicketDetailRequest;
import com.enigmacamp.evening.payload.request.ticket.CreateRequest;
import com.enigmacamp.evening.payload.request.ticket.UpdateRequest;
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
    public Ticket create(String eventId, CreateRequest ticket) {
        Event event = eventService.getById(eventId);
        Ticket newTicket = ticketRepository.save(new Ticket(event, ticket.getTitle(),
                ticket.getDescription(), ticket.getPrice(), ticket.getStock(),
                ticket.getMinAmount(), ticket.getMaxAmount()));

        Set<TicketDetail> ticketDetails = new HashSet<>();
        for(String eventDetail: ticket.getTicketDetail()){
            EventDetail eventDetails  = eventDetailService.getById(eventDetail);
            if(matchingEventAndDetails(event, eventDetails)){
                TicketDetail ticketDetail = new TicketDetail(newTicket, eventDetails);
                ticketDetailService.create(ticketDetail);
                ticketDetails.add(ticketDetail);
            }else{
                throw new InvalidInputException("event details only accept inherited corresponding events.");
            }
        }
        newTicket.setTicketDetail(ticketDetails);
        return ticketRepository.save(newTicket);
    }

    @Override
    public Ticket readByIdOrThrowNotFound(String eventId, String id) {
        Event event = eventService.getById(eventId);
        Optional<Ticket> ticket = ticketRepository.getActiveTicket(id);
        if(ticket.isPresent()){
            if(matchingTicketAndEvents(ticket.get(), event)){
                return ticket.get();
            }
        }
        throw new NotFoundException("Sorry, ticket that you looking for is unavailable!");
    }


    @Override
    public Ticket update(String eventId, String id, UpdateRequest ticket) {
        try{
            Ticket currentTicket = readByIdOrThrowNotFound(eventId, id);
            if(ticket.getTitle() != null) {
                currentTicket.setTitle(ticket.getTitle());
            }
            if(ticket.getDescription() != null){
                currentTicket.setDescription(ticket.getDescription());
            }
            if(ticket.getPrice() != null){
                currentTicket.setPrice(ticket.getPrice());
            }
            if(ticket.getStock() != null){
                currentTicket.setStock(ticket.getStock());
            }
            if(ticket.getMinAmount() != null){
                currentTicket.setMinAmount(ticket.getMinAmount());
            }
            if(ticket.getMaxAmount() != null){
                currentTicket.setMaxAmount(ticket.getMaxAmount());
            }
            Ticket save = ticketRepository.save(currentTicket);
            return inputValidation(currentTicket) ? save : null;
        }catch(Exception ex){
            throw new InvalidInputException(ex.getMessage());
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
        Specification<Ticket> specification = TicketSpecification.getSpecification(eventId, ticketDTO);
        return ticketRepository.findAll(specification, pageable);
    }

    @Override
    public TicketResponse convertTicket(Ticket ticket) {
        return new TicketResponse(ticket.getId(), ticket.getEvent().getName(), ticket.getTitle(), ticket.getDescription(),
                ticket.getPrice(), ticket.getStock(), ticket.getMinAmount(), ticket.getMaxAmount(),
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

    @Override
    public Ticket addNewDetail(String ticketId, String eventId, TicketDetailRequest ticketDetailRequest) {
        Ticket ticket = readByIdOrThrowNotFound(eventId, ticketId);
        EventDetail eventDetails  = eventDetailService.getById(ticketDetailRequest.getTicketDetail());
        if(matchingEventAndDetails(ticket.getEvent(), eventDetails)) {
            for (TicketDetail ticketDetails: ticket.getTicketDetail()) {
                if(ticketDetails.getEventDetail().equals(eventDetails)){
                    throw new InvalidInputException("current event details is already on the list");
                }
            }
            TicketDetail ticketDetail = new TicketDetail(ticket, eventDetails);
            ticket.getTicketDetail().add(ticketDetail);
            ticketRepository.save(ticket);
            ticketDetailService.create(ticketDetail);
        }
        return readByIdOrThrowNotFound(eventId, ticketId);
    }

    @Override
    public void deleteDetailTicket(String eventId, String ticketId, String detailsId) {
        Ticket ticket = readByIdOrThrowNotFound(eventId, ticketId);
        ticket.getTicketDetail().remove(ticketDetailService.readByIdOrThrowNotFound(detailsId));
        ticketRepository.save(ticket);
        ticketDetailService.delete(detailsId);
    }

    private Boolean matchingEventAndDetails(Event event, EventDetail eventDetail){
        return event.equals(eventDetail.getEvent());
    }

    private Boolean matchingTicketAndEvents(Ticket ticket, Event event){
        return event.equals(ticket.getEvent());
    }

    private boolean inputValidation(Ticket ticket){
        if(ticket.getMaxAmount() < ticket.getMinAmount()){
            throw new InvalidInputException("[max amount] of ticket must be more than [min amount]");
        }
        return true;
    }
}