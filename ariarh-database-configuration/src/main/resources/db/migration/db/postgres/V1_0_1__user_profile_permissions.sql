CREATE SCHEMA IF NOT EXISTS admin;
CREATE SEQUENCE admin.id_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE admin."user"
(
    id                 bigint DEFAULT nextval('admin.id_user_seq'::regclass) NOT NULL,
    email              character varying(320) UNIQUE                         NOT NULL,
    password_hash      character varying(64),
    nom                character varying(80),
    prenom             character varying(80),
    telephone          character varying(50),
    date_de_naissance  DATE,
    picture            bytea,
    activated          boolean                                               NOT NULL,
    nb_attempts        integer,
    non_locked         boolean,
    created_by         character varying(50),
    created_date       timestamp without time zone,
    last_modified_by   character varying(50),
    last_modified_date timestamp without time zone,
    gouvernorat_id     bigint,
    genre_id           bigint,
    profile_id         bigint,

    PRIMARY KEY (id)
);

CREATE SEQUENCE admin.profile_profile_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE admin.profile
(
    profile_id         bigint DEFAULT nextval('admin.profile_profile_id_seq'::regclass) NOT NULL,
    codemetier         character varying(256)                                           NOT NULL UNIQUE,
    description        character varying(256),
    created_by         character varying(50)                                            NOT NULL,
    created_date       timestamp without time zone                                      NOT NULL,
    last_modified_by   character varying(50),
    last_modified_date timestamp without time zone,

    PRIMARY KEY (profile_id)
);

CREATE SEQUENCE admin.permission_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE admin.permission
(
    permission_id      bigint NOT NULL DEFAULT nextval('admin.permission_id_seq'::regclass),
    description        character varying(256) COLLATE pg_catalog."default",
    role               character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created_by         character varying(50) COLLATE pg_catalog."default"  NOT NULL,
    created_date       timestamp without time zone                         NOT NULL,
    last_modified_by   character varying(50) COLLATE pg_catalog."default",
    last_modified_date timestamp without time zone,
    grouped            varchar(255),

    PRIMARY KEY (permission_id)
);


CREATE TABLE admin.profile_permission
(
    profile_id    bigint NOT NULL,
    permission_id bigint NOT NULL,
    PRIMARY KEY (profile_id, permission_id)
);

INSERT INTO admin.permission(permission_id, grouped, role, description, created_date, created_by, last_modified_date,
                             last_modified_by)
values (nextval('admin.permission_id_seq'), 'Utilisateurs - Administration', 'Consulter-Utilisateur',
        'Accès à la gestion de utilisateurs', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.permission_id_seq'), 'Utilisateurs - Administration', 'Créer-Utilisateur', null,
        CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.permission_id_seq'), 'Utilisateurs - Administration', 'Modifier-Utilisateur', null,
        CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.permission_id_seq'), 'Utilisateurs - Administration', 'Activer/Desactiver-Utilisateur', null,
        CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.permission_id_seq'), 'Utilisateurs - Administration', 'Rechercher-Utilisateur', null,
        CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.permission_id_seq'), 'Utilisateurs - Utilisation', 'Connecter-Avec-Login', null,
        CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.permission_id_seq'), 'Profil - Administration', 'Créer-Profil', null, CURRENT_TIMESTAMP,
        'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.permission_id_seq'), 'Profil - Administration', 'Modifier-Profil', null, CURRENT_TIMESTAMP,
        'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.permission_id_seq'), 'Profil - Administration', 'Consulter-Profil', 'Accès à la page de profil',
        CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.permission_id_seq'), 'Profil - Utilisation', 'Consulter-Profil', null, CURRENT_TIMESTAMP,
        'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.permission_id_seq'), 'Profil - Utilisation', 'Modifier-Informations-Personnelles', null,
        CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');

INSERT INTO admin.profile_permission(profile_id, permission_id)
SELECT 1, permission_id
from admin.permission;

INSERT INTO admin.profile(profile_id, codeMetier, created_date, created_by, last_modified_date, last_modified_by)
VALUES (nextval('admin.profile_profile_id_seq'), 'ADMIN', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.profile_profile_id_seq'), 'PARTENAIRE', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system'),
       (nextval('admin.profile_profile_id_seq'), 'USER', CURRENT_TIMESTAMP, 'system', CURRENT_TIMESTAMP, 'system');

INSERT INTO admin."user" (id, profile_id, gouvernorat_id, nom, prenom, email, password_hash, telephone, genre_id, date_de_naissance, activated, non_locked, nb_attempts,
                          created_date, created_by, last_modified_date, last_modified_by)
VALUES (nextval('admin.id_user_seq'), 1,20, 'Admin', 'Admin', 'admin@ariarh.com',
        '$2a$10$1JpuJTbHxlaO4iSZ4V71w.CZlcQLl.HBG1gVzGfbazL6DkCI3GPUK', null, null, null, true, true, 0, CURRENT_TIMESTAMP, 'system',
        CURRENT_TIMESTAMP, 'system');

