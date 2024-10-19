package com.itpeac.ariarh.middleoffice.repository.jpa;

import com.itpeac.ariarh.middleoffice.domain.User;
import com.itpeac.ariarh.middleoffice.domain.User_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class UserSpecifications {

    private UserSpecifications() {
    }

    static public Specification<User> byCriteriaSpec(User model) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotBlank(model.getEmail()))
                predicates.add(cb.like(cb.lower(root.get(User_.email)), "%" + model.getEmail().toLowerCase() + "%"));

            if (StringUtils.isNotBlank(model.getNom()))
                predicates.add(cb.like(cb.lower(root.get(User_.nom)), "%" + model.getNom().toLowerCase() + "%"));
            if (StringUtils.isNotBlank(model.getPrenom()))
                predicates.add(cb.like(cb.lower(root.get(User_.prenom)), "%" + model.getPrenom().toLowerCase() + "%"));
            if (model.getActivated() != null)
                predicates.add(cb.equal(root.get(User_.nonLocked), model.getActivated()));
            if (model.getCreatedDate() != null) {

                predicates.add(cb.equal(cb.function("date", Date.class, root.get(User_.CREATED_DATE)), model.getCreatedDate()));
            }
            if (model.getLastModifiedDate() != null) {
                predicates.add(cb.equal(cb.function("date", Date.class, root.get(User_.LAST_MODIFIED_DATE)), model.getLastModifiedDate()));
            }
            if (StringUtils.isNotBlank(model.getLastModifiedBy())) {
                predicates.add(cb.like(cb.lower(root.get(User_.LAST_MODIFIED_BY)), "%" + model.getLastModifiedBy().toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(model.getCreatedBy())) {
                predicates.add(cb.like(cb.lower(root.get(User_.CREATED_BY)), "%" + model.getCreatedBy().toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}

