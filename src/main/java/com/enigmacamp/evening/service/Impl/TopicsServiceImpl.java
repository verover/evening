package com.enigmacamp.evening.service.Impl;

import com.enigmacamp.evening.entity.Topics;
import com.enigmacamp.evening.exception.NotFoundException;
import com.enigmacamp.evening.repository.TopicsRepository;
import com.enigmacamp.evening.service.TopicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionScoped;
import java.util.List;
import java.util.Optional;

@Service
@TransactionScoped
public class TopicsServiceImpl implements TopicsService {


    @Autowired
    TopicsRepository topicsRepository;

    @Override
    public Topics save(Topics topics) {
        return topicsRepository.save(topics);
    }

    @Override
    public List<Topics> findAll() {
        return topicsRepository.findAll();
    }

    @Override
    public Topics getById(String id) {
        return findByOrThrowNotFound(id);
    }

    @Override
    public Page<Topics> listPage(Pageable pageable) {
        return this.topicsRepository.findAll(pageable);
    }

    @Override
    public Topics update(String id, Topics topics) {
        topics.setTopicsId(id);
        return topicsRepository.save(topics);
    }

    public Topics findByOrThrowNotFound(String id) {
        Optional<Topics> topics = this.topicsRepository.findById(id);
        if (!topics.isPresent()) {
            throw new NotFoundException("Topics is not found");
        }
        return topics.get();
    }
}
