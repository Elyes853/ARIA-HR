package com.itpeac.ariarh.middleoffice.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Category.class)
public abstract class Category_ extends com.itpeac.ariarh.middleoffice.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Category, Long> id;
	public static volatile SingularAttribute<Category, String> label;
	public static volatile ListAttribute<Category, Question> questionList;

	public static final String ID = "id";
	public static final String LABEL = "label";
	public static final String QUESTION_LIST = "questionList";

}

