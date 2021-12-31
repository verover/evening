package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.dto.OrganizerDTO;
import com.enigmacamp.evening.entity.Organizer;
import com.enigmacamp.evening.repository.OrganizerRepository;
import com.enigmacamp.evening.service.OrganizerService;
import com.enigmacamp.evening.specification.OrganizerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    public Page<Organizer> listWithPage(Pageable pageable, OrganizerDTO organizerDTO) {
        Specification <Organizer> specification = OrganizerSpecification.getSpecification(organizerDTO);
       return organizerRepository.findAll(specification, pageable);
    }

    @Override
    public Organizer updateOrganizerById(Organizer organizer) {
        Organizer currentOrganizer = getOrganizerById(organizer.getId());

        currentOrganizer.setName(organizer.getName());
        currentOrganizer.setAddress(organizer.getAddress());
        currentOrganizer.setOrganization_email(organizer.getOrganization_email());
        currentOrganizer.setOrganization_phone(organizer.getOrganization_phone());
        currentOrganizer.setWebsite(organizer.getWebsite());

        return organizerRepository.save(currentOrganizer);
    }

    @Override
    public String delete(String id) {
        Organizer organizer = getOrganizerById(id);
        organizerRepository.delete(organizer);
        return String.format("Organizer dengan id %s berhasil dihapus",organizer.getId());
    }


}
