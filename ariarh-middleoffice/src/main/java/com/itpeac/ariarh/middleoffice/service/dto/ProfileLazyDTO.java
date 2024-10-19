package com.itpeac.ariarh.middleoffice.service.dto;

import java.time.Instant;

import com.itpeac.ariarh.middleoffice.domain.Profile;

public class ProfileLazyDTO {

    private Long id;
    private String codeMetier;
    private String description;
    private String createdBy;
    private Instant createdDate;
    private String lastModifiedBy;
    private Instant lastModifiedDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeMetier() {
        return codeMetier;
    }

    public void setCodeMetier(String codeMetier) {
        this.codeMetier = codeMetier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ProfileLazyDTO() {

    }


    public ProfileLazyDTO(Profile profile) {
        super();
        this.id = profile.getId();
        this.codeMetier = profile.getCodeMetier();
        this.description = profile.getDescription();
        this.createdBy = profile.getCreatedBy();
        this.createdDate = profile.getCreatedDate();
        this.lastModifiedBy = profile.getLastModifiedBy();
        this.lastModifiedDate = profile.getLastModifiedDate();

    }

    public Profile toLazyProfile() {
        Profile profile = new Profile();
        profile.setCodeMetier(codeMetier);
        profile.setId(id);
        profile.setDescription(description);

        return profile;
    }


}

