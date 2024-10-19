package com.itpeac.ariarh.middleoffice.service.impl;

import com.itpeac.ariarh.middleoffice.domain.Conversation;
import com.itpeac.ariarh.middleoffice.domain.Offer;
import com.itpeac.ariarh.middleoffice.repository.jpa.ConversationRepository;
import com.itpeac.ariarh.middleoffice.service.ConversationService;
import com.itpeac.ariarh.middleoffice.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    OfferService offerService;

    @Autowired
    ConversationRepository conversationRepository;
    @Override
    public Conversation saveConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    @Override
    public Conversation findById (Long id){
        return conversationRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public void refresh(Conversation conversation) {
        entityManager.refresh(conversation);

    }

    @Override
    public String systemInstruction(String path) {

        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            System.out.println(content);

            return content;

        } catch (IOException e) {
            return e.getMessage();
        }
    }
    @Override
    public String replaceContent(String filePath, Map<String, String> variables) {
        Path path = Paths.get(filePath);
        try {
            // Read the template content
            String template = Files.readString(path);

            // Replace variables in the template
            return replaceVariables(template, variables);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String replaceVariables(String template, Map<String, String> variables) {
        String content = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            content = content.replace(entry.getKey(), entry.getValue());
        }
        return content;
    }

    @Override
    public String systemVariables(Long idOffre, String candidateName){

        Offer offer = offerService.getOfferById(idOffre);

        Map<String, String> variables= new HashMap<>();
        variables.put("{{title}}", offer.getTitle());
        variables.put("{{responsibilities}}", offer.getResponsibilities());
        variables.put("{{description}}", offer.getDescription());
        variables.put("{{requiredSkills}}", offer.getRequiredSkills());
        variables.put("{{experienceRequired}}", offer.getExperienceRequired());
        variables.put("{{candidateName}}", candidateName);
/*
        variables.put("{{resume}}", resumeCV);
*/

        String text = replaceContent("C:\\Users\\elyes\\enet'com\\PFE\\Itpeac\\iria\\ariarh-middleoffice\\src\\main\\java\\com\\itpeac\\ariarh\\middleoffice\\web\\rest\\docs\\instructionRH.txt", variables);

        return text;
    }




}



