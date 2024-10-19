package com.itpeac.ariarh.middleoffice.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permission.class)
public abstract class Permission_ extends com.itpeac.ariarh.middleoffice.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<Permission, String> role;
	public static volatile SingularAttribute<Permission, String> grouped;
	public static volatile SingularAttribute<Permission, String> description;
	public static volatile SingularAttribute<Permission, Long> id;

	public static final String ROLE = "role";
	public static final String GROUPED = "grouped";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

