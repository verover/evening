package com.enigmacamp.evening.dto;

import com.enigmacamp.evening.entity.EventDetail;
import lombok.Data;

import java.util.List;

@Data
public class EventRequest {
    private String organizerId,name,bannerImage,category,topics;
    private List<EventDetail> eventDetails;
}
