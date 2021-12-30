package com.enigmacamp.evening.service.Impl;

import java.util.Optional;

import javax.transaction.Transactional;

import com.enigmacamp.evening.entity.TicketDetail;
import com.enigmacamp.evening.exception.NotFoundException;
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

    @Override
    public TicketDetail readByIdOrThrowNotFound(String id) {
        Optional<TicketDetail> detail = ticketDetailRepository.findById(id);
        if(detail.isPresent()){
            return detail.get();
        }else{
            throw new NotFoundException("Sorry, ticket that you looking for is unavailable!");
        }
    }

    @Override
    public TicketDetail update(String id, TicketDetail ticketDetail) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        
    }
}
