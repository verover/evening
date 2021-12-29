package com.enigmacamp.evening.repository;

import com.enigmacamp.evening.entity.Topics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicsRepository extends JpaRepository<Topics,String> {
}
