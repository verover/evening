package com.enigmacamp.evening.service;

import com.enigmacamp.evening.dto.OrganizerDTO;
import com.enigmacamp.evening.entity.Organizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface OrganizerService {

    Organizer create(Organizer organizer);

    Organizer getOrganizerById(String id);

    Page<Organizer> listWithPage(Pageable pageable, OrganizerDTO organizerDTO);

    Organizer updateOrganizerById(Organizer organizer);

    String delete(String id);



}
