package com.itpeac.ariarh.middleoffice.service.impl;

import com.itpeac.ariarh.middleoffice.domain.Conversation;
import com.itpeac.ariarh.middleoffice.domain.Message;
import com.itpeac.ariarh.middleoffice.repository.jpa.MessageRepository;
import com.itpeac.ariarh.middleoffice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Transactional
    @Override
    public void refresh(Message message) {
        entityManager.refresh(message);

    }
}
