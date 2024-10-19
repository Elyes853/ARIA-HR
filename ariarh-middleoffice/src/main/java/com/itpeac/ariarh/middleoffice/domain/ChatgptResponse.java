package com.itpeac.ariarh.middleoffice.domain;

import java.util.List;

public class ChatgptResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
