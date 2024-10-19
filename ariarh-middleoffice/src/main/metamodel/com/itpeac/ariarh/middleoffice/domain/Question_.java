package com.itpeac.ariarh.middleoffice.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Question.class)
public abstract class Question_ extends com.itpeac.ariarh.middleoffice.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Question, String> question;
	public static volatile SingularAttribute<Question, Category> category_id;
	public static volatile SingularAttribute<Question, Long> id;

	public static final String QUESTION = "question";
	public static final String CATEGORY_ID = "category_id";
	public static final String ID = "id";

}

