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

    @Override
    public Organizer getOrganizerById(String id) {
        Optional<Organizer> organizer = organizerRepository.findById(id);

        if(organizer.isPresent()){
            return organizer.get();
        }else {
            throw new RuntimeException(String.format("Organizer dengan id %s tidak ditemukan", id));
        }
    }

    @Override
    public List<Organizer> list() {
        return organizerRepository.findAll();

    }


}
