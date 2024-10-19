CREATE SEQUENCE IF NOT EXISTS id_conversation_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE IF NOT EXISTS conversation
(
    id bigint DEFAULT nextval('id_conversation_seq'::regclass) NOT NULL,
    candidate_id bigint NOT NULL,
    PRIMARY KEY (id)
    );

