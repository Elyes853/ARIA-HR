package com.itpeac.ariarh.middleoffice.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@NamedEntityGraph(name = "User.profile",
        attributeNodes = @NamedAttributeNode("profile")
)
@Table(name = "user", schema = "admin")
public class User extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String password;

    @NotNull
    @Column(name = "date_de_naissance")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @NotNull
    @Column(name = "telephone")
    private String telephone;

    @NotNull
    @Column(nullable = false)
    private Boolean activated;

    @Column(name = "nb_attempts", nullable = false)
    private Integer nbAttempts;

    @NotNull
    @Column(name = "non_locked", nullable = false)
    private Boolean nonLocked;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "picture")
    private byte[] picture;

    // HACK to bypass picture binary loading when using a DTO
    @Formula("case when picture is not null then true else false end ")
    private Boolean hasPicture;

    // getter & setter

    public Boolean getNonLocked() {
        return nonLocked;
    }

    public void setNonLocked(Boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Integer getNbAttempts() {
        return nbAttempts;
    }

    public void setNbAttempts(Integer nb_attempts) {
        this.nbAttempts = nb_attempts;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    @JsonBackReference
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    // constructor

    public User() {
        super();
    }

    public User(Long id, String nom, String prenom, String email, String password, LocalDate date, String telephone, Boolean activated, Integer nbAttempts, Boolean nonLocked, Profile profile, byte[] picture, Boolean hasPicture) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.date = date;
        this.telephone = telephone;
        this.activated = activated;
        this.nbAttempts = nbAttempts;
        this.nonLocked = nonLocked;
        this.profile = profile;
        this.picture = picture;
        this.hasPicture = hasPicture;
    }

    public User(String nom, String prenom, String email,
                String password, boolean activated) {
        super();

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.activated = activated;


    }

    public User(Long id, String nom, String prenom, String email,
                String password, boolean activated) {
        super();
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.activated = activated;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email +  ", activated=" + activated + ", nb_attempts=" + nbAttempts + "]";
    }

    public Boolean getHasPicture() {
        return hasPicture;
    }

    public void setHasPicture(Boolean hasPicture) {
        this.hasPicture = hasPicture;
    }



}
