package com.itpeac.ariarh.middleoffice.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Candidate.class)
public abstract class Candidate_ {

	public static volatile SingularAttribute<Candidate, String> resume;
	public static volatile SingularAttribute<Candidate, String> score;
	public static volatile SingularAttribute<Candidate, String> role;
	public static volatile SingularAttribute<Candidate, Boolean> isOpen;
	public static volatile SingularAttribute<Candidate, CV> cv;
	public static volatile SingularAttribute<Candidate, String> phone;
	public static volatile SingularAttribute<Candidate, String> name;
	public static volatile SingularAttribute<Candidate, String> location;
	public static volatile SingularAttribute<Candidate, Long> id;
	public static volatile ListAttribute<Candidate, Conversation> conversationList;
	public static volatile SingularAttribute<Candidate, String> email;
	public static volatile SingularAttribute<Candidate, String> token;

	public static final String RESUME = "resume";
	public static final String SCORE = "score";
	public static final String ROLE = "role";
	public static final String IS_OPEN = "isOpen";
	public static final String CV = "cv";
	public static final String PHONE = "phone";
	public static final String NAME = "name";
	public static final String LOCATION = "location";
	public static final String ID = "id";
	public static final String CONVERSATION_LIST = "conversationList";
	public static final String EMAIL = "email";
	public static final String TOKEN = "token";

}

