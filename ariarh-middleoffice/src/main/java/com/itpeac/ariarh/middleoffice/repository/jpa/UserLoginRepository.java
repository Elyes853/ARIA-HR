package com.itpeac.ariarh.middleoffice.repository.jpa;

import com.itpeac.ariarh.middleoffice.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserLoginRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "User.profile")
    User findOneByEmail(final String email);
}
