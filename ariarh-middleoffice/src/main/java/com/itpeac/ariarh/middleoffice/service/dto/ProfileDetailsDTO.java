package com.itpeac.ariarh.middleoffice.service.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itpeac.ariarh.middleoffice.domain.Permission;
import com.itpeac.ariarh.middleoffice.domain.Profile;
import com.itpeac.ariarh.middleoffice.domain.User;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDetailsDTO {

    private Long id;
    private String codeMetier;
    private String description;
    private List<Permission> permissions = new ArrayList<Permission>();
    private List<User> users = new ArrayList<>();
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

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public ProfileDetailsDTO() {

    }

    public ProfileDetailsDTO(Profile profile) {
        super();
        this.id = profile.getId();
        this.codeMetier = profile.getCodeMetier();
        this.description = profile.getDescription();
        this.users = profile.getUsers();
        this.permissions = profile.getPermissions();
        this.createdBy = profile.getCreatedBy();
        this.createdDate = profile.getCreatedDate();
        this.lastModifiedBy = profile.getLastModifiedBy();
        this.lastModifiedDate = profile.getLastModifiedDate();

    }

    public Profile toProfile() {
        Profile profile = new Profile();
        profile.setCodeMetier(codeMetier);
        profile.setId(id);
        profile.setDescription(description);
        profile.setUsers(users);
        profile.setPermissions(permissions);
        return profile;
    }


}
