package com.enigmacamp.evening.repository;

import java.util.Optional;

import com.enigmacamp.evening.entity.Ticket;

import com.enigmacamp.evening.payload.response.TicketResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String>{
    Page<Ticket> findAll(Specification<Ticket> specification, Pageable pageable);

    @Query(value = "SELECT t FROM Ticket t WHERE t.isDeleted = false AND t.id = ?1")
    Optional<Ticket> getActiveTicket(String id);

}
