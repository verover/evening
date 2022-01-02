package com.enigmacamp.evening.payload.request.ticket;

import java.util.Set;

import com.enigmacamp.evening.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequest {
    @NotEmpty(message = "Title is required")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "title is required")
    private String title;
    private String description;
    @NotNull(message = "price is required")
    private Integer price;
    @NotNull(message = "stock is required")
    private Integer stock;
    @NotNull(message = "minimal amount of tickets is required")
    @Min(value = 1, message = "maximal amount must be greater than 1")
    private Integer minAmount;
    @NotNull(message = "maximal amount of tickets is required")
    @Min(value = 1, message = "maximal amount must be greater than 1")
    private Integer maxAmount;
    @NotNull(message = "ticket details is required.")
    @NotEmpty(message = "ticket detail must have one events")
    private Set<String> ticketDetail;
}
