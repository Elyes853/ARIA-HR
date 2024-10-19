package com.itpeac.ariarh.middleoffice.web.rest;

import com.itpeac.ariarh.middleoffice.domain.Offer;
import com.itpeac.ariarh.middleoffice.service.OfferService;
import com.itpeac.ariarh.middleoffice.web.rest.mapper.OfferMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class OfferController{
    @Autowired
    private OfferMapper offerMapper;

    @Autowired
    private OfferService offerService;

    @PostMapping("/api/offer")
    ResponseEntity<?> createOffer(@RequestBody Offer newOffer){
        System.out.println("requete post/offer reçue");
        try {
            Offer savedOffer = offerService.saveOffer(newOffer);
            return ResponseEntity.ok(savedOffer);
        }catch (Exception e){
            e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/api/offers")
    ResponseEntity<?> getOffers(){
        try{
            List<Offer> offersList = offerService.getOfferslist();
            return ResponseEntity.ok(offerMapper.fromListEntityToListDto(offersList));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/api/offers/{id}")
    ResponseEntity<?> getOfferById(@PathVariable Long id) {
        try {
            Offer offer = offerService.getOfferById(id);
            if (offer != null) {
                return ResponseEntity.ok(offerMapper.fromEntityToDto(offer));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/api/offers/{id}/candidates")
    ResponseEntity<?> getCandidatesPerOffer(@PathVariable Long id) {
        try {
            Offer offer = offerService.getOfferById(id);
            if (offer != null) {
                return ResponseEntity.ok(offer.getCandidatesList());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



}