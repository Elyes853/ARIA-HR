CREATE SEQUENCE IF NOT EXISTS id_offer_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS offer
(
    id                 bigint DEFAULT nextval('id_offer_seq'::regclass) NOT NULL,
    title              character varying(50)                                NOT NULL,
    description        TEXT                              NOT NULL,
    responsibilities   TEXT,
    required_skills    TEXT,
    experience_required TEXT,
    education          TEXT,
    location           character varying(100),
    contract_type      character varying(100),
    benefits           TEXT,
    application_process TEXT,
    application_deadline timestamp without time zone,
    created_by         character varying(50),
    created_date       timestamp without time zone,
    last_modified_by   character varying(50),
    last_modified_date timestamp without time zone,
    PRIMARY KEY (id)
    );
