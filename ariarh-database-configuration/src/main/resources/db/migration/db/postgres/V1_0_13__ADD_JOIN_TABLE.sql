CREATE TABLE IF NOT EXISTS offer_candidate
(
    offer_id    bigint NOT NULL,
    candidate_id bigint NOT NULL,
    PRIMARY KEY (offer_id, candidate_id)
    );