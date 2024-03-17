--liquibase formatted sql
--changeset daneker:create-users-table
CREATE TABLE users
(
    id         bigserial PRIMARY KEY,
    first_name varchar(255)        NOT NULL,
    last_name  varchar(255)        NOT NULL,
    username   varchar(255) UNIQUE NOT NULL,
    password   varchar(255)        NOT NULL,
    tag        varchar(255) UNIQUE NOT NULL,
    email      varchar(255) UNIQUE NOT NULL,
    phone      varchar(255),
    created_time timestamp with time zone,
    last_modified_time timestamp with time zone
);
--rollback drop table users;