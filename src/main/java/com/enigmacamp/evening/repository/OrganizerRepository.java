package com.enigmacamp.evening.repository;

import com.enigmacamp.evening.entity.Organizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrganizerRepository extends JpaRepository <Organizer, String>{

    Page<Organizer> findAll(Specification<Organizer> specification, Pageable pageable);
}
