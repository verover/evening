package com.enigmacamp.evening.service;

import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.entity.TicketDetail;
import com.enigmacamp.evening.payload.response.TicketDetailResponse;

import java.util.List;
import java.util.Set;

public interface TicketDetailService {
    TicketDetail create(TicketDetail ticketDetail);
    TicketDetail readByIdOrThrowNotFound(String id);
    TicketDetail update(String id, TicketDetail ticketDetail);
    List<TicketDetailResponse> readByTicketId(Set<TicketDetail> ticket);
    void delete(String id);
}
