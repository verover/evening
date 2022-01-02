package com.enigmacamp.evening.payload.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventResponse<T> {
    private Boolean status;
    private List<String> messages = new ArrayList<>();
    private T data;
}
