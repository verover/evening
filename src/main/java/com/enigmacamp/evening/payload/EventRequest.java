package com.enigmacamp.evening.payload;

import com.enigmacamp.evening.entity.EventDetail;
import lombok.Data;

import java.util.List;

@Data
public class EventRequest {
    private String eventId,organizerId,name,bannerImage,category,topics;
    private List<EventDetail> eventDetails;
}
