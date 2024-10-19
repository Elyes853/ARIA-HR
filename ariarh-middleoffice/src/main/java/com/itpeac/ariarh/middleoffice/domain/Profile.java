package com.itpeac.ariarh.middleoffice.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@NamedEntityGraph(name = "Profile.permissions",
        attributeNodes = @NamedAttributeNode("permissions")
)
@Table(name = "Profile", schema = "admin")
public class Profile extends AbstractAuditingEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false, unique = true)
    private Long id;

    @NotBlank
    @Column(name = "codemetier", nullable = false, unique = true)
    private String codeMetier;

    @Column(name = "description", length = 256)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Permission.class)
    @JoinTable(name = "Profile_Permission", schema = "admin", joinColumns = {
            @JoinColumn(name = "profile_id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "permission_id", nullable = false, unique = false)}, uniqueConstraints = {
            @UniqueConstraint(columnNames = {"profile_id", "permission_id"})})
    private List<Permission> permissions;

    @OneToMany(mappedBy = "profile")
    private List<User> users;

    @JsonManagedReference
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        if (CollectionUtils.isNotEmpty(users)) {
            for (User user : users) {
                user.setProfile(this);
            }
        }
        this.users = users;
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

    public Profile(@NotBlank String codeMetier, String description, List<Permission> permissions, List<User> users) {
        super();
        this.codeMetier = codeMetier;
        this.description = description;
    }

    public Profile() {
        super();
    }


}
