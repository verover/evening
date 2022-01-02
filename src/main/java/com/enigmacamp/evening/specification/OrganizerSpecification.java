package com.enigmacamp.evening.specification;

import com.enigmacamp.evening.dto.OrganizerDTO;
import com.enigmacamp.evening.entity.Organizer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class OrganizerSpecification {

    public static Specification<Organizer> getSpecification(OrganizerDTO organizerDTO) {
        return new Specification<Organizer>() {
            @Override
            public Predicate toPredicate(Root<Organizer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (organizerDTO.getSearchByOrganizerName() != null){
                    Predicate organizerNamePredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            "%" + organizerDTO.getSearchByOrganizerName() + "%");
                    predicates.add(organizerNamePredicate);

                }

                if(organizerDTO.getSearchByOrganizerEmail() != null){
                    Predicate organizerEmailPredicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("organization_email")),
                            "%" + organizerDTO.getSearchByOrganizerEmail( ) + "%");
                    predicates.add(organizerEmailPredicate);

                }

                Predicate[] arrayPredicates = predicates.toArray(new Predicate[predicates.size()]);
                return criteriaBuilder.and(arrayPredicates);
            }
        };
    }
}
