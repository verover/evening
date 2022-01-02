package com.enigmacamp.evening.repository;

import com.enigmacamp.evening.entity.EventDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDetailRepository extends JpaRepository<EventDetail,String> {

    @Query("SELECT d from EventDetail d where isDeleted = false")
    Page<EventDetail> findAllWithPage(Pageable pageable);
}
