package com.itpeac.ariarh.middleoffice.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Entity
@Table(name = "CV")
public class CV {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "body", nullable = false)
    private String body;
    @NotNull
    @Column(name = "path", nullable = false)
    private String path;
    @NotNull
    @Column(name = "eTag", nullable = false)
    private String eTag;

    @Transient
    @OneToOne(mappedBy = "candidate")
    private Candidate candidate;

        public CV() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
