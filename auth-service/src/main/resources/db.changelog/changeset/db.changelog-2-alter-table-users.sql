--liquibase formatted sql
--changeset daneker:alter-table-users
alter table users add column email_verified boolean not null default false;
alter table users add column otp int;
alter table users add column otp_creation_time timestamp with time zone;