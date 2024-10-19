package com.itpeac.ariarh.middleoffice.service;

import com.itpeac.ariarh.middleoffice.domain.Conversation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface    ConversationService {
    Conversation saveConversation(Conversation conversation);

    Conversation findById(Long id);

    void refresh(Conversation conversation);

    String systemInstruction(String path);


    String replaceContent(String filePath, Map<String, String> variables);

    String systemVariables(Long id, String candidateName);


}
