ALTER TABLE authorization
    DROP FOREIGN KEY FK_AUTHORIZATION_ON_USER;

ALTER TABLE user_authorizations
    DROP FOREIGN KEY fk_useaut_on_authorization;

ALTER TABLE user_authorizations
    DROP FOREIGN KEY fk_useaut_on_user;

DROP TABLE user_authorizations;

ALTER TABLE authorization
    DROP COLUMN user_id;