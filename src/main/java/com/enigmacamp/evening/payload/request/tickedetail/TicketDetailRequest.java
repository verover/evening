package com.enigmacamp.evening.payload.request.tickedetail;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDetailRequest {
    @NotNull(message = "event details is required")
    String ticketDetail;
}
