package com.enigmacamp.evening.service;

import com.enigmacamp.evening.entity.TicketDetail;

public interface TicketDetailService {
    TicketDetail create(TicketDetail ticketDetail);
    TicketDetail readByIdOrThrowNotFound(String id);
    TicketDetail update(String id, TicketDetail ticketDetail);
    void delete(String id);
}
