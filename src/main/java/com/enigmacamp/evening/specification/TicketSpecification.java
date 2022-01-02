package com.enigmacamp.evening.specification;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.persistence.criteria.*;

import com.enigmacamp.evening.dto.TicketDTO;
import com.enigmacamp.evening.entity.Ticket;

import org.springframework.data.jpa.domain.Specification;

public class TicketSpecification {
    public static Specification<Ticket> getSpecification(String eventId, TicketDTO ticketDTO){
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

                // Buggy, please dont implement this
                if(ticketDTO.getFirstDate() != null && ticketDTO.getLastDate() != null){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String firstDate = sdf.format(Date.valueOf(ticketDTO.getFirstDate()));
                    SimpleDateFormat sdfEnd = new SimpleDateFormat("yyyy-MM-dd");
                    String lastDate = sdfEnd.format(Date.valueOf(ticketDTO.getLastDate()));

                    Predicate dateInBetween = criteriaBuilder.between(
                            root.join("ticketDetail").get("eventDetail").get("date"), firstDate, lastDate);
                    predicates.add(dateInBetween);
                }

                Predicate notDeletedTicket = criteriaBuilder.isFalse(root.get("isDeleted"));
                predicates.add(notDeletedTicket);

                Predicate joinedEvent = criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get("event").get("eventId")), eventId
                );
                predicates.add(joinedEvent);

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
