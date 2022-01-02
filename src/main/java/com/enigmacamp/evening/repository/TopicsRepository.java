package com.enigmacamp.evening.repository;

import com.enigmacamp.evening.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicsRepository extends JpaRepository<Topics,String> {
}
