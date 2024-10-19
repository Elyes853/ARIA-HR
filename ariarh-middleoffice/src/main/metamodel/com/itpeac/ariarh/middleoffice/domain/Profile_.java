package com.itpeac.ariarh.middleoffice.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Profile.class)
public abstract class Profile_ extends com.itpeac.ariarh.middleoffice.domain.AbstractAuditingEntity_ {

	public static volatile ListAttribute<Profile, Permission> permissions;
	public static volatile SingularAttribute<Profile, String> description;
	public static volatile SingularAttribute<Profile, Long> id;
	public static volatile ListAttribute<Profile, User> users;
	public static volatile SingularAttribute<Profile, String> codeMetier;

	public static final String PERMISSIONS = "permissions";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String USERS = "users";
	public static final String CODE_METIER = "codeMetier";

}

