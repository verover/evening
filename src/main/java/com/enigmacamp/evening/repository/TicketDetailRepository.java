package com.enigmacamp.evening.repository;

import com.enigmacamp.evening.entity.TicketDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketDetailRepository extends JpaRepository<TicketDetail, String>{
}
