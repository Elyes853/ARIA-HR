package com.itpeac.ariarh.middleoffice.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.itpeac.ariarh.middleoffice.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<User, LocalDate> date;
	public static volatile SingularAttribute<User, Boolean> nonLocked;
	public static volatile SingularAttribute<User, Profile> profile;
	public static volatile SingularAttribute<User, String> telephone;
	public static volatile SingularAttribute<User, String> nom;
	public static volatile SingularAttribute<User, byte[]> picture;
	public static volatile SingularAttribute<User, Integer> nbAttempts;
	public static volatile SingularAttribute<User, Boolean> hasPicture;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> prenom;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Boolean> activated;

	public static final String DATE = "date";
	public static final String NON_LOCKED = "nonLocked";
	public static final String PROFILE = "profile";
	public static final String TELEPHONE = "telephone";
	public static final String NOM = "nom";
	public static final String PICTURE = "picture";
	public static final String NB_ATTEMPTS = "nbAttempts";
	public static final String HAS_PICTURE = "hasPicture";
	public static final String PASSWORD = "password";
	public static final String ID = "id";
	public static final String PRENOM = "prenom";
	public static final String EMAIL = "email";
	public static final String ACTIVATED = "activated";

}

