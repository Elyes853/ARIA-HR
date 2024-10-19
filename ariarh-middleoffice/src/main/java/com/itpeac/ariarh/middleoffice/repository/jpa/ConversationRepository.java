package com.itpeac.ariarh.middleoffice.repository.jpa;

import com.itpeac.ariarh.middleoffice.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long > {
}
