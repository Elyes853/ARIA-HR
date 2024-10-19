package com.itpeac.ariarh.middleoffice.repository.jpa;

import com.itpeac.ariarh.middleoffice.domain.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {


    Optional<Profile> findOneByCodeMetier(String codeMetier);

    @EntityGraph(value = "Profile.permissions")
    Optional<Profile> findOneById(Long id);

    Profile findBycodeMetierIgnoreCase(String string);


}
