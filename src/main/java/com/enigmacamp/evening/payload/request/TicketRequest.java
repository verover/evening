package com.enigmacamp.evening.payload.request;

import java.util.List;
import java.util.Set;

import com.enigmacamp.evening.entity.Event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    private String title, description;
    private Integer price, stock, minAmmount, maxAmmount;
    private Set<String> ticketDetail;
}
