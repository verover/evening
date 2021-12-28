package com.enigmacamp.evening.service.Impl;

import javax.transaction.Transactional;

import com.enigmacamp.evening.entity.TicketDetail;
import com.enigmacamp.evening.repository.TicketDetailRepository;
import com.enigmacamp.evening.service.TicketDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TicketDetailServiceImpl implements TicketDetailService {

    @Autowired
    TicketDetailRepository ticketDetailRepository;

    @Override
    public TicketDetail create(TicketDetail ticketDetail) {
        return ticketDetailRepository.save(ticketDetail);
    }
}
