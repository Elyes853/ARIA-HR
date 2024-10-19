package com.itpeac.ariarh.middleoffice.service;

import com.itpeac.ariarh.middleoffice.domain.Offer;

import java.util.List;

public interface OfferService {
    public Offer saveOffer(Offer offer);

    public List<Offer> getOfferslist();

    Offer getOfferById(Long id);
}
