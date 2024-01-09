--liquibase formatted sql
--changeset daneker:create-reset_password_token-table
CREATE TABLE reset_password_token
(
    id          bigserial primary key,
    token       VARCHAR(255)             NOT NULL,
    user_id     INT                      NOT NULL,
    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
--rollback drop table reset_password_token;
