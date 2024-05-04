ALTER TABLE session
    ADD session_id BIGINT NULL;

ALTER TABLE session
    DROP COLUMN token_id;

ALTER TABLE session
    ADD PRIMARY KEY (session_id);