package com.enigmacamp.evening.payload.response;

import com.enigmacamp.evening.entity.EventDetail;
import com.enigmacamp.evening.entity.TicketDetail;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TicketResponse {
    String id, event, title, description;
    Integer price, stock, minAmmount, maxAmmount;
    List<EventDetail> validFor;
}
