package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.entity.Organizer;
import com.enigmacamp.evening.repository.OrganizerRepository;
import com.enigmacamp.evening.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizerServiceImpl implements OrganizerService {

    @Autowired
    private OrganizerRepository organizerRepository;

    @Override
    public Organizer create(Organizer organizer) {
       return organizerRepository.save(organizer);

    }




}
