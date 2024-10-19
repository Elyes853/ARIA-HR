CREATE SEQUENCE IF NOT EXISTS id_test_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS test
(
    id                 bigint DEFAULT nextval('id_test_seq'::regclass) NOT NULL,
    nature             character varying(50)                           NOT NULL,
    created_by         character varying(50),
    created_date       timestamp without time zone,
    last_modified_by   character varying(50),
    last_modified_date timestamp without time zone,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS test_question
(
    test_id    bigint NOT NULL,
    question_id bigint NOT NULL,
    PRIMARY KEY (test_id, question_id)
    );