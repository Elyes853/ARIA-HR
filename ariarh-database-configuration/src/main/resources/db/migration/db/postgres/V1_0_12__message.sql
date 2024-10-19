CREATE SEQUENCE IF NOT EXISTS id_message_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS message
(
    id                 bigint DEFAULT nextval('id_message_seq'::regclass) NOT NULL,
    sender VARCHAR(10) NOT NULL ,
    content                TEXT NOT NULL ,
    conversation_id        bigint NOT NULL ,
    PRIMARY KEY (id)
);