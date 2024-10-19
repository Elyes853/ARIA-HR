package com.itpeac.ariarh.middleoffice.domain;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ {

	public static volatile SingularAttribute<Message, String> role;
	public static volatile SingularAttribute<Message, Conversation> conversation_id;
	public static volatile SingularAttribute<Message, Long> id;
	public static volatile SingularAttribute<Message, String> content;
	public static volatile SingularAttribute<Message, LocalDateTime> sendingTime;

	public static final String ROLE = "role";
	public static final String CONVERSATION_ID = "conversation_id";
	public static final String ID = "id";
	public static final String CONTENT = "content";
	public static final String SENDING_TIME = "sendingTime";

}

