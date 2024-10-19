package com.itpeac.ariarh.middleoffice.service;

import com.itpeac.ariarh.middleoffice.domain.Conversation;
import com.itpeac.ariarh.middleoffice.domain.Message;

public interface MessageService {

    public Message saveMessage(Message message);

    public void refresh(Message message);
}
