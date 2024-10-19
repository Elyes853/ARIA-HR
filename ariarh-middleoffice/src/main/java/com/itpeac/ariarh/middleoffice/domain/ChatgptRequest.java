package com.itpeac.ariarh.middleoffice.domain;

import java.util.ArrayList;
import java.util.List;

public class ChatgptRequest {
    private String model;
    private List<Message> messages = new ArrayList<>();

    public ChatgptRequest() {
        // Default constructor for serialization
    }

    public ChatgptRequest(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    // Getter and setter methods for 'model'
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Getter and setter methods for 'messages'
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
