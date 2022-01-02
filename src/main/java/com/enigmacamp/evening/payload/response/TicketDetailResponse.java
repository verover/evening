package com.enigmacamp.evening.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketDetailResponse {
    String id, eventDetail, date, location;
}
