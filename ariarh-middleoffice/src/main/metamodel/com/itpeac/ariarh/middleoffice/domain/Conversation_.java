package com.itpeac.ariarh.middleoffice.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Conversation.class)
public abstract class Conversation_ {

	public static volatile SingularAttribute<Conversation, Candidate> candidate;
	public static volatile ListAttribute<Conversation, Message> messagesList;
	public static volatile SingularAttribute<Conversation, Long> id;

	public static final String CANDIDATE = "candidate";
	public static final String MESSAGES_LIST = "messagesList";
	public static final String ID = "id";

}

