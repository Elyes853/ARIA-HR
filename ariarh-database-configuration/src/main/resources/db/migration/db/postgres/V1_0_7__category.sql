CREATE SEQUENCE IF NOT EXISTS id_category_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS category
(
    id                 bigint DEFAULT nextval('id_category_seq'::regclass) NOT NULL,
    label             character varying(50)             NOT NULL,
    created_by         character varying(50),
    created_date       timestamp without time zone,
    last_modified_by   character varying(50),
    last_modified_date timestamp without time zone,
    PRIMARY KEY (id)

    );