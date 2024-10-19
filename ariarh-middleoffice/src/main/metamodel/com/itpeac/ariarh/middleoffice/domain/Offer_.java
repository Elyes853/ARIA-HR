package com.itpeac.ariarh.middleoffice.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Offer.class)
public abstract class Offer_ extends com.itpeac.ariarh.middleoffice.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Offer, String> benefits;
	public static volatile SingularAttribute<Offer, String> education;
	public static volatile SingularAttribute<Offer, String> contractType;
	public static volatile SingularAttribute<Offer, String> description;
	public static volatile ListAttribute<Offer, Candidate> candidatesList;
	public static volatile SingularAttribute<Offer, String> title;
	public static volatile SingularAttribute<Offer, String> responsibilities;
	public static volatile SingularAttribute<Offer, String> experienceRequired;
	public static volatile SingularAttribute<Offer, Date> applicationDeadline;
	public static volatile SingularAttribute<Offer, String> requiredSkills;
	public static volatile SingularAttribute<Offer, String> location;
	public static volatile SingularAttribute<Offer, Long> id;
	public static volatile SingularAttribute<Offer, String> applicationProcess;

	public static final String BENEFITS = "benefits";
	public static final String EDUCATION = "education";
	public static final String CONTRACT_TYPE = "contractType";
	public static final String DESCRIPTION = "description";
	public static final String CANDIDATES_LIST = "candidatesList";
	public static final String TITLE = "title";
	public static final String RESPONSIBILITIES = "responsibilities";
	public static final String EXPERIENCE_REQUIRED = "experienceRequired";
	public static final String APPLICATION_DEADLINE = "applicationDeadline";
	public static final String REQUIRED_SKILLS = "requiredSkills";
	public static final String LOCATION = "location";
	public static final String ID = "id";
	public static final String APPLICATION_PROCESS = "applicationProcess";

}

