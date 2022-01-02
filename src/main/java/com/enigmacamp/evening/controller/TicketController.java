package com.enigmacamp.evening.controller;

import com.enigmacamp.evening.dto.TicketDTO;
import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.entity.TicketDetail;
import com.enigmacamp.evening.payload.request.tickedetail.TicketDetailRequest;
import com.enigmacamp.evening.payload.request.ticket.CreateRequest;
import com.enigmacamp.evening.payload.request.ticket.UpdateRequest;
import com.enigmacamp.evening.payload.response.TicketResponse;
import com.enigmacamp.evening.service.TicketDetailService;
import com.enigmacamp.evening.service.TicketService;
import com.enigmacamp.evening.util.PageResponse;
import com.enigmacamp.evening.util.WebResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/events/{id}/tickets")
public class TicketController {

    @Autowired
    TicketService ticketService;
    
    @GetMapping
    public ResponseEntity<WebResponse<PageResponse<TicketResponse>>> listWithPage(
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @PathVariable(name = "id") String eventId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "availability", defaultValue = "true") Boolean avail,
            @RequestParam(name = "begin", required = false) String beginDate,
            @RequestParam(name = "end", required = false) String endDate
    ) {
        Pageable pageable = PageRequest.of(page, size);
        TicketDTO ticketDTO = new TicketDTO(name, avail, beginDate, endDate);
        Page<Ticket> events = ticketService.readAllTicket(eventId, pageable, ticketDTO);
        PageResponse<TicketResponse> pageResponse;
        if(events.getTotalElements() > 0){
            pageResponse = new PageResponse<>(
                    ticketService.convertAllTicket(events.getContent()),
                    events.getTotalElements(),
                    events.getTotalPages(),
                    page,
                    size
            );
            WebResponse<PageResponse<TicketResponse>> response = new WebResponse("all tickets are loaded",
                    pageResponse);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } else {
            pageResponse = new PageResponse<>(
                    null,
                    events.getTotalElements(),
                    events.getTotalPages(),
                    page,
                    size
            );
            WebResponse<PageResponse<TicketResponse>> response = new WebResponse("no tickets are available",
                    pageResponse);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<WebResponse<TicketResponse>> getTickets(
            @PathVariable(name = "id") String eventId,
            @PathVariable(name = "ticketId") String ticketId){
        Ticket ticket = ticketService.readByIdOrThrowNotFound(eventId, ticketId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new WebResponse<>("Ticket are loaded successfully", ticketService.convertTicket(ticket)));
    }

    @PostMapping
    public ResponseEntity<WebResponse<TicketResponse>> createNewTicket(@PathVariable(name = "id") String eventId,
        @Valid @RequestBody CreateRequest createRequest){
        Ticket ticket = ticketService.create(eventId, createRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new WebResponse<>("successfully add new tickets", ticketService.convertTicket(ticket)));
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<WebResponse<TicketResponse>> deleteTicket(@PathVariable(name = "ticketId") String ticketId,
                                                                    @PathVariable(name = "id") String eventId){
        Ticket ticket = ticketService.readByIdOrThrowNotFound(eventId, ticketId);
        ticketService.delete(eventId,ticketId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new WebResponse<>("your tickets are deleted", ticketService.convertTicket(ticket)));
    }

    @PatchMapping("/{ticketId}")
    public ResponseEntity<WebResponse<TicketResponse>> updateTicket(@PathVariable(name = "ticketId") String ticketId,
                                                                    @PathVariable(name = "id") String eventId,
                                                                    @RequestBody UpdateRequest ticketRequest){
        Ticket ticket = ticketService.update(eventId, ticketId, ticketRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new WebResponse<>("update ticket successful", ticketService.convertTicket(ticket)));
    }

    // Ticket Details
    @PostMapping("/{ticketId}/details")
    public ResponseEntity<WebResponse<TicketResponse>> addDetailTicket(@PathVariable(name = "id") String eventId,
                                                                       @PathVariable(name = "ticketId") String ticketId,
                                                                       @Valid @RequestBody TicketDetailRequest ticketDetail){
        Ticket ticket = ticketService.addNewDetail(ticketId, eventId, ticketDetail);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new WebResponse<>("successfully add ticket details", ticketService.convertTicket(ticket)));
    }

    @DeleteMapping("/{ticketId}/details/{detailsId}")
    public ResponseEntity<WebResponse<String>> deleteDetailTicket(@PathVariable(name = "ticketId") String ticketId,
                                                                          @PathVariable(name = "id") String eventId,
                                                                          @PathVariable(name = "detailsId") String detailsId){
        ticketService.deleteDetailTicket(eventId,ticketId,detailsId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new WebResponse<>("your tickets are deleted", "Successfuly deleted"));
    }
}
