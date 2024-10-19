package com.itpeac.ariarh.middleoffice.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="message")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "sender", nullable = false)
    private String role;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation_id;

    @Column(name = "sending_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime sendingTime;

    public Message() {
        // Default constructor for serialization
    }

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    // Getter and setter methods for 'user'
    public String getRole() {
        return role;
    }

    public void setRole(String sender) {
        this.role = sender;
    }

    // Getter and setter methods for 'query'
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conversation getConversation_id() {
        return conversation_id;
    }

    public void setConversation_id(Conversation conversation_id) {
        this.conversation_id = conversation_id;
    }

    public LocalDateTime getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(LocalDateTime sendingTime) {
        this.sendingTime = sendingTime;
    }
}