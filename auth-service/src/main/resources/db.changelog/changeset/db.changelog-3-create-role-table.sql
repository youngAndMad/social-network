--liquibase formatted sql
--changeset daneker:create-role-table
create table role
(
    id bigserial primary key,
    role varchar(255) not null ,
    user_id bigint not null,
    created_time timestamp with time zone,
    last_modified_time timestamp with time zone,
    foreign key (user_id) references users(id)
)