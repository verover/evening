package com.enigmacamp.evening.service;

import com.enigmacamp.evening.entity.Organizer;

import java.util.List;

public interface OrganizerService {

    Organizer create(Organizer organizer);

    Organizer getOrganizerById(String id);


}
