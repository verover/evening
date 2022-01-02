package com.enigmacamp.evening.payload.request.ticket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest {
    String title, description;
    Integer price, stock, minAmount, maxAmount;
}
