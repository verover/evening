package com.enigmacamp.evening.specification;

import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.enigmacamp.evening.dto.TicketDTO;
import com.enigmacamp.evening.entity.Ticket;

import org.springframework.data.jpa.domain.Specification;

public class TicketSpecification {
    public static Specification<Ticket> getSpecification(TicketDTO ticketDTO){
        return new Specification<Ticket>() {
            @Override
            public Predicate toPredicate(Root<Ticket> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> predicates = new ArrayList<>();

                if(ticketDTO.getSearchByName() != null){
                    Predicate namePredicate = criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + ticketDTO.getSearchByName().toLowerCase() + "%");
                    predicates.add(namePredicate);
                }
                
                if(ticketDTO.getAvailableTicket()){
                    Predicate ticketPredicate = criteriaBuilder.gt(
                        root.get("stock"), 0);
                    predicates.add(ticketPredicate);
                }

                Predicate notDeletedTicket = criteriaBuilder.isFalse(root.get("isDeleted"));
                predicates.add(notDeletedTicket);

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
