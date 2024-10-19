package com.itpeac.ariarh.middleoffice.web.rest.mapper;

import com.itpeac.ariarh.middleoffice.domain.Offer;
import com.itpeac.ariarh.middleoffice.service.dto.OfferDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OfferMapper implements Mapper<Offer, OfferDTO> {

    @Override
    public OfferDTO fromEntityToDto(Offer offer) {
        if (offer != null) {
            OfferDTO offerDTO = new OfferDTO();
            offerDTO.setId(offer.getId());
            offerDTO.setTitle(offer.getTitle());
            offerDTO.setDescription(offer.getDescription());
            offerDTO.setResponsibilities(offer.getResponsibilities());
            offerDTO.setRequiredSkills(offer.getRequiredSkills());
            offerDTO.setExperienceRequired(offer.getExperienceRequired());
            offerDTO.setEducation(offer.getEducation());
            offerDTO.setLocation(offer.getLocation());
            offerDTO.setContractType(offer.getContractType());
            offerDTO.setBenefits(offer.getBenefits());
            offerDTO.setApplicationProcess(offer.getApplicationProcess());
            offerDTO.setApplicationDeadline(offer.getApplicationDeadline());
            offerDTO.setApplicationDeadline(offer.getApplicationDeadline());


            // Set other attributes here

            return offerDTO;
        }
        return null;
    }

    @Override
    public Offer fromDTOtoEntity(OfferDTO offerDTO) {
        if (offerDTO != null) {
            Offer offer = new Offer();
            offer.setId(offerDTO.getId());
            offer.setTitle(offerDTO.getTitle());
            offer.setDescription(offerDTO.getDescription());
            offer.setResponsibilities(offerDTO.getResponsibilities());
            offer.setRequiredSkills(offerDTO.getRequiredSkills());
            offer.setExperienceRequired(offerDTO.getExperienceRequired());
            offer.setEducation(offerDTO.getEducation());
            offer.setLocation(offerDTO.getLocation());
            offer.setContractType(offerDTO.getContractType());
            offer.setBenefits(offerDTO.getBenefits());
            offer.setApplicationProcess(offerDTO.getApplicationProcess());
            offer.setApplicationDeadline(offerDTO.getApplicationDeadline());

            // Set other attributes here

            return offer;
        }
        return null;
    }

    public List<OfferDTO> fromListEntityToListDto(List<Offer> offers) {
        return offers.stream().map(this::fromEntityToDto).collect(Collectors.toList());
    }
}
