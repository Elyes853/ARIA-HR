package com.itpeac.ariarh.middleoffice.domain;

import com.itpeac.ariarh.middleoffice.service.dto.CandidateDTO;

public class CandidateRequest {
    private CandidateDTO user;
    private Long offerId;

    public CandidateDTO getUser() {
        return user;
    }

    public void setUser(CandidateDTO user) {
        this.user = user;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public CandidateRequest() {
    }
}
