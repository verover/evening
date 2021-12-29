package com.enigmacamp.evening.dto;


import lombok.Data;

import java.util.Date;

public class EventDTO {
    private String searchByName;

    public EventDTO(String searchByName) {
        this.searchByName = searchByName;
    }

    public String getSearchByName() {
        return searchByName;
    }

    public void setSearchByName(String searchByName) {
        this.searchByName = searchByName;
    }
}
