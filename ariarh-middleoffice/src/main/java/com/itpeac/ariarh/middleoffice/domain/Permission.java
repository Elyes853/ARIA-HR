package com.itpeac.ariarh.middleoffice.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "Permission", schema = "admin")


public class Permission extends AbstractAuditingEntity {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "role", nullable = false, updatable = false)
    private String role;

    @Column(name = "description", length = 256)
    private String description;

    private String grouped;

    public Permission() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGrouped() {
        return grouped;
    }

    public void setGrouped(String grouped) {
        this.grouped = grouped;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(getClass()).
                append("role", role).
                append("description", description).
                append("group", grouped).
                toString();
    }
}
