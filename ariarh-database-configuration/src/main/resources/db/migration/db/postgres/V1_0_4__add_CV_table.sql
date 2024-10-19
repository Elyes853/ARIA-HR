CREATE SEQUENCE IF NOT EXISTS id_cv_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS CV
(
    id                 bigint DEFAULT nextval('id_cv_seq'::regclass) NOT NULL,
    body text NOT NULL ,
    path                character varying(1000) NOT NULL ,
    eTag                character varying(1000) NOT NULL ,
    created_by         character varying(50),
    created_date       timestamp without time zone,
    last_modified_by   character varying(50),
    last_modified_date timestamp without time zone,
    PRIMARY KEY (id)
);