package com.enigmacamp.evening.payload.request;

import com.enigmacamp.evening.entity.EventDetail;
import lombok.Data;

import java.util.List;

@Data
public class EventRequest {
    private String eventId,organizerId,name,bannerImage,categoryId,topicsId;
    private List<EventDetail> eventDetails;
}
