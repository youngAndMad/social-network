--liquibase formatted sql
--changeset daneker:add-extension-to-file_meta_data-table
ALTER TABLE file_meta_data
    ADD COLUMN extension varchar;
