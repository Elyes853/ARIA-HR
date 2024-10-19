package com.itpeac.ariarh.middleoffice.repository.jpa;

import com.itpeac.ariarh.middleoffice.domain.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {



}
