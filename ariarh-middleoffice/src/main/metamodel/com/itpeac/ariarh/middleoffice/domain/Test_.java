package com.itpeac.ariarh.middleoffice.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Test.class)
public abstract class Test_ extends com.itpeac.ariarh.middleoffice.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Test, String> nature;
	public static volatile ListAttribute<Test, Question> questionsList;
	public static volatile SingularAttribute<Test, Long> id;

	public static final String NATURE = "nature";
	public static final String QUESTIONS_LIST = "questionsList";
	public static final String ID = "id";

}

