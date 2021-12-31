package com.enigmacamp.evening.repository;

import com.enigmacamp.evening.entity.Ticket;
import com.enigmacamp.evening.entity.TicketDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketDetailRepository extends JpaRepository<TicketDetail, String>{

}
