package com.itpeac.ariarh.middleoffice.domain;

import javax.persistence.Entity;
import javax.persistence.Table;


public class Choice {
    private int index;
    private Message message;

    public Choice(int index, Message message) {
        this.index = index;
        this.message = message;
    }

    public Choice() {
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
