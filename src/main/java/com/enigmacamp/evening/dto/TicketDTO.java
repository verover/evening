package com.enigmacamp.evening.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TicketDTO {

    private String searchByName;
    private Boolean AvailableTicket;
    private String firstDate;
    private String lastDate;
}
