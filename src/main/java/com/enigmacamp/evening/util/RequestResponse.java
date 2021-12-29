package com.enigmacamp.evening.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequestResponse<T> {
    private Boolean status;
    private List<String> messages = new ArrayList<>();
    private T data;
}
