--liquibase formatted sql
--changeset daneker:create-role-table
create table authority
(
    id                 bigserial primary key,
    name               varchar(255) not null,
    role_id            bigint       not null,
    created_time       timestamp with time zone,
    last_modified_time timestamp with time zone,
    foreign key (role_id) references role (id)
)