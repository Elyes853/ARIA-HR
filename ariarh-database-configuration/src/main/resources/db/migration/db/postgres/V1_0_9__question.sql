CREATE SEQUENCE IF NOT EXISTS id_question_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS question
(
    id                 bigint DEFAULT nextval('id_question_seq'::regclass) NOT NULL,
    question           text                          NOT NULL ,
    category_id     bigint  NOT NULL ,
    created_by         character varying(50),
    created_date       timestamp without time zone,
    last_modified_by   character varying(50),
    last_modified_date timestamp without time zone,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category(id)

    );

