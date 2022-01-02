package com.enigmacamp.evening.service;

import com.enigmacamp.evening.entity.Topics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicsService {
    Topics save(Topics topics);
    List<Topics> findAll();
    Topics getById(String id);
    Page<Topics> listPage(Pageable pageable);
    Topics update(String id,Topics topics);
}
