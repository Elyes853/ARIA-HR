package com.itpeac.ariarh.middleoffice.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name="offer")
public class Offer extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "responsibilities")
    private String responsibilities;

    @Column(name = "required_skills")
    private String requiredSkills;

    @Column(name = "experience_required")
    private String experienceRequired;

    @Column(name = "education")
    private String education;

    @Column(name = "location")
    private String location;

    @Column(name = "contract_type")
    private String contractType;

    @Column(name = "benefits")
    private String benefits;

    @Column(name = "application_process")
    private String applicationProcess;

    @Column(name = "application_deadline")
    @Temporal(TemporalType.TIMESTAMP)
    private Date applicationDeadline;

    @ManyToMany
    @JoinTable(
            name = "offer_candidate",
            joinColumns = @JoinColumn(name = "offer_id"),
            inverseJoinColumns = @JoinColumn(name = "candidate_id")
    )
    private List<Candidate> candidatesList = new ArrayList<>();


    public Offer(){}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title; }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getResponsibilities() { return responsibilities; }

    public void setResponsibilities(String responsibilities) { this.responsibilities = responsibilities; }

    public String getRequiredSkills() { return requiredSkills; }

    public void setRequiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; }

    public String getExperienceRequired() { return experienceRequired; }

    public void setExperienceRequired(String experienceRequired) { this.experienceRequired = experienceRequired; }

    public String getEducation() { return education; }

    public void setEducation(String education) { this.education = education; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getContractType() { return contractType; }

    public void setContractType(String contractType) { this.contractType = contractType; }

    public String getBenefits() { return benefits; }

    public void setBenefits(String benefits) { this.benefits = benefits; }

    public String getApplicationProcess() { return applicationProcess; }

    public void setApplicationProcess(String applicationProcess) { this.applicationProcess = applicationProcess; }

    public Date getApplicationDeadline() { return applicationDeadline; }

    public void setApplicationDeadline(Date applicationDeadline) { this.applicationDeadline = applicationDeadline; }

    public List<Candidate> getCandidatesList() {return candidatesList;}

    public void setCandidatesList(List<Candidate> candidatesList) {this.candidatesList = candidatesList;}

    @Override
    public String toString() {
        String offerDescription = "Titre de l'offre: " + this.title+
                "Description de l'offre: " + this.description+
                "Éducation requise: " + this.education+
                "Responsabilités: " + this.responsibilities+
                "Compétences requises: " + this.requiredSkills+
                "Expérience Requise: " + this.experienceRequired+
                "Éducation: " + this.education+
                "Emplacement: " + this.location;

        return offerDescription;
    }

    public Map<String, String> toMap(){

        Map<String, String> offerMap = new HashMap<>();
        offerMap.put("{{title}}",this.title);
        offerMap.put("{{responsibilities}}",this.responsibilities);
        offerMap.put("{{requiredSkills}}",this.requiredSkills);
        offerMap.put("{{experienceRequired}}",this.experienceRequired);
        return null;
    }
}
