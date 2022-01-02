package com.enigmacamp.evening.specification;

import com.enigmacamp.evening.dto.EventDTO;
import com.enigmacamp.evening.entity.Event;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class EventSpecification {
    public static Specification<Event> getSpecification(EventDTO eventDTO){
        return new Specification<Event>() {
            @Override
            public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                ArrayList<Predicate> predicates = new ArrayList<>();
                if(eventDTO.getSearchByName() != null){
                    Predicate byName = criteriaBuilder
                            .like(criteriaBuilder.
                                    lower(root.get("name")),"%" + eventDTO.getSearchByName().toLowerCase() + "%");
                    predicates.add(byName);
                }

                if(eventDTO.getSearchByTopics() != null){
                    Predicate byTopics = criteriaBuilder.like((criteriaBuilder.lower(root.get("topics").get("name"))), "%" + eventDTO.getSearchByTopics() + "%");
                    predicates.add(byTopics);
                }

                Predicate notDeletedTicket = criteriaBuilder.isFalse(root.get("isDeleted"));
                predicates.add(notDeletedTicket);

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
