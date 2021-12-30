package com.enigmacamp.evening.dto;


import lombok.Data;

import java.util.Date;

public class EventDTO {
    private String searchByName;
    private String searchByTopics;


    public EventDTO(String searchByName, String searchByTopics) {
        this.searchByName = searchByName;
        this.searchByTopics = searchByTopics;
    }

    public String getSearchByName() {
        return searchByName;
    }

    public void setSearchByName(String searchByName) {
        this.searchByName = searchByName;
    }

    public String getSearchByTopics() {
        return searchByTopics;
    }

    public void setSearchByTopics(String searchByTopics) {
        this.searchByTopics = searchByTopics;
    }
}
