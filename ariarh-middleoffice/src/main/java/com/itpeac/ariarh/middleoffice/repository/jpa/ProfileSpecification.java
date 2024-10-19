package com.itpeac.ariarh.middleoffice.repository.jpa;

import com.itpeac.ariarh.middleoffice.domain.Permission;
import com.itpeac.ariarh.middleoffice.domain.Permission_;
import com.itpeac.ariarh.middleoffice.domain.Profile;
import com.itpeac.ariarh.middleoffice.domain.Profile_;
import com.itpeac.ariarh.middleoffice.service.dto.ProfileCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileSpecification {

    private ProfileSpecification() {
    }

    static public Specification<Profile> byCriteriaSpec(ProfileCriteria criteria) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(criteria.getCodeMetier())) {
                predicates.add(cb.like(cb.lower(root.get(Profile_.codeMetier)), "%" + criteria.getCodeMetier().toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(criteria.getDescription())) {
                predicates.add(cb.like(cb.lower(root.get(Profile_.description)), "%" + criteria.getDescription().toLowerCase() + "%"));
            }
            if (StringUtils.isNotBlank(criteria.getCreatedBy())) {
                predicates.add(cb.like(cb.lower(root.get(Profile_.createdBy)), "%" + criteria.getCreatedBy().toLowerCase() + "%"));
            }
            if (criteria.getCreatedDate() != null) {
                predicates.add(cb.equal(cb.function("date", Date.class, root.get(Profile_.createdDate)), criteria.getCreatedDate()));
            }
            if (StringUtils.isNotBlank(criteria.getLastModifiedBy())) {
                predicates.add(cb.like(cb.lower(root.get(Profile_.lastModifiedBy)), "%" + criteria.getLastModifiedBy().toLowerCase() + "%"));
            }
            if (criteria.getCreatedDate() != null) {
                predicates.add(cb.equal(cb.function("date", Date.class, root.get(Profile_.lastModifiedDate)), criteria.getLastModifiedDate()));
            }
            Join<Profile, Permission> profilePermission = null;
            Predicate permDescInClause = null;
            Predicate permRoleInClause = null;
            if (StringUtils.isNotBlank(criteria.getPermissionRoles())) {
                profilePermission = root.join(Profile_.permissions);
                permRoleInClause = cb.like(cb.lower(profilePermission.get(Permission_.role)), "%" + criteria.getPermissionRoles().toLowerCase() + "%");
            }
            if (StringUtils.isNotBlank(criteria.getPermissionDescs())) {
                profilePermission = profilePermission != null ? profilePermission : root.join(Profile_.permissions);
                permDescInClause = cb.like(cb.lower(profilePermission.get(Permission_.description)), "%" + criteria.getPermissionDescs().toLowerCase() + "%");
            }
            if (permDescInClause != null && permRoleInClause != null) {
                predicates.add(cb.or(permDescInClause, permRoleInClause));
            } else if (permDescInClause != null) {
                predicates.add(permDescInClause);
            } else if (permRoleInClause != null) {
                predicates.add(permRoleInClause);
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
