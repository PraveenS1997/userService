CREATE TABLE session
(
    token_id BIGINT       NOT NULL,
    token    VARCHAR(255) NULL,
    is_valid BIT(1)       NOT NULL,
    user_id  BIGINT       NULL,
    CONSTRAINT pk_session PRIMARY KEY (token_id)
);

CREATE TABLE user
(
    id       BIGINT       NOT NULL,
    name     VARCHAR(255) NULL,
    email    VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_users_email ON user (email);

ALTER TABLE session
    ADD CONSTRAINT FK_SESSION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);