package com.enigmacamp.evening.repository;

import com.enigmacamp.evening.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.websocket.server.PathParam;

public interface EventRepository extends JpaRepository<Event,String> {

    @Query("SELECT v from Event v where isDeleted = false")
    Page<Event> findAll(Pageable pageable);

    @Query("SELECT v FROM Event v WHERE LOWER(v.topics.name) LIKE LOWER(:name)")
    Page<Event> findByNameTopic(@PathParam("name") String name,Pageable pageable);

    @Query("SELECT v FROM Event v WHERE LOWER(v.name) LIKE LOWER(:name)")
    Page<Event> findByName(@PathParam("name") String name,Pageable pageable);

//    @Query("SELECT v FROM Event v WHERE Event.EventDetail.date between :from and :until")
//    Page<Event> findBetweenDate(@PathParam("from") String from, @PathParam("until") String until, Pageable pageable);

}

