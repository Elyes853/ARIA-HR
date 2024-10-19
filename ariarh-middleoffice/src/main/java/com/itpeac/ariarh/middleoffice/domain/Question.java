package com.itpeac.ariarh.middleoffice.domain;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

    @Entity
    @Table(name="question")
    public class Question extends AbstractAuditingEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotNull
        @Column(name = "question", nullable = false)
        private String question;
        @JsonBackReference
        @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinColumn(name = "category_id", nullable = false)
        private Category category_id;



    public Question() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String c_name) {
        this.question = c_name;
    }

    public Category getCategoryId() {
        return category_id;
    }

    public void setCategoryId(Category categoryId) {
        this.category_id = categoryId;
    }
}

