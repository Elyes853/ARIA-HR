package com.itpeac.ariarh.middleoffice.service.impl;

import com.itpeac.ariarh.middleoffice.domain.Offer;
import com.itpeac.ariarh.middleoffice.repository.jpa.OfferRepository;
import com.itpeac.ariarh.middleoffice.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Override
    public Offer saveOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public List<Offer> getOfferslist(){ return offerRepository.findAll(); }

    @Override
    public Offer getOfferById(Long id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        return optionalOffer.orElse(null); // Return the offer if found, otherwise return null
    }
}