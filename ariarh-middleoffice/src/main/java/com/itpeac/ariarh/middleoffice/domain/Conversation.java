package com.itpeac.ariarh.middleoffice.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name="conversation")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Conversation {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @JsonBackReference
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "candidate_id")
        private Candidate candidate;


        @JsonManagedReference
        @OneToMany(mappedBy = "conversation_id",  fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        private List<Message> messagesList;

        // getters and setters


        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Candidate getCandidate() {
                return candidate;
        }

        public void setCandidate(Candidate candidate_id) {
                this.candidate = candidate_id;
        }

        public List<Message> getMessagesList() {
                return messagesList;
        }

        public void setMessagesList(List<Message> messagesList) {
                this.messagesList = messagesList;
        }
}


