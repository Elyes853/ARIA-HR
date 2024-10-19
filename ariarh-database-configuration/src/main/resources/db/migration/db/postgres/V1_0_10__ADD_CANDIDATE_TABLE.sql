CREATE SEQUENCE IF NOT EXISTS id_candidate_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS candidates
(
    id                 bigint DEFAULT nextval('id_candidate_seq'::regclass) NOT NULL,
    name               varchar(255) NOT NULL,
    email              varchar(255) NOT NULL,
    location           varchar(255),
    phone              varchar(20),
    role               varchar(50),
    cv_id              bigint,
    PRIMARY KEY (id),
    CONSTRAINT fk_cv_id FOREIGN KEY (cv_id) REFERENCES cv(id) ON DELETE CASCADE
    );
-- Add any additional constraints or indices as needed
