package com.itpeac.ariarh.middleoffice.common;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Simple User that also keep track of the primary key.
 */
public class UserDetailsWithId extends User {
    private static final long serialVersionUID = 1L;
    private final Long id;
    private String nom;
    private String prenom;
    private long idProfile;


    public UserDetailsWithId(String username, String password, boolean enabled, boolean accountNonExpired,
                             boolean credentialsNonExpired, boolean accountNonLocked,
                             Collection<? extends GrantedAuthority> authorities, Long id, String nom, String prenom, Long idProfile) {

        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.idProfile = idProfile;


    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public long getIdProfile() {
        return idProfile;
    }


}
