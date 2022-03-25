package com.oleh.chui.model.service.util.filter;

import com.oleh.chui.model.entity.Tour;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TourSpecification implements Specification<Tour> {

    private final List<SearchCriteria> criteriaList;

    public TourSpecification() {
        this.criteriaList = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        criteriaList.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Tour> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

            for (SearchCriteria criteria : criteriaList) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(criteriaBuilder.equal(
                        root.get(criteria.getKey()), criteria.getValue()
                ));
            } else if (criteria.getOperation().equals(SearchOperation.DATE_AFTER_THAN)) {
                predicates.add(criteriaBuilder.greaterThan(
                        root.get(criteria.getKey()), (LocalDate) criteria.getValue()
                ));
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString()
                ));
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get(criteria.getKey()), criteria.getValue().toString()
                ));
            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                predicates.add(
                        criteriaBuilder.in(root.get(criteria.getKey()).get(criteria.getInnerKey())).value(criteria.getValue())
                );
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
