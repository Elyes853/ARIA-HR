package com.itpeac.ariarh.middleoffice.repository.jpa;

import com.itpeac.ariarh.middleoffice.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Page<User> findAll(Pageable pageable);

    List<User> findByNom(String nom);

    List<User> findByPrenom(String prenom);

    Optional<User> findOneByEmail(String email);


    Optional<User> findOneById(Long id);

    @Transactional
    @Modifying
    @Query("update User u set u.picture = ?1 where u.id = ?2")
    int updateUserPicture(byte[] picture, Long id);

    @Query("Select u.picture from User u where u.id = ?1")
    byte[] getUserPicture(Long id);


}

