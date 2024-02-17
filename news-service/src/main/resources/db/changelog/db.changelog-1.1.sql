--changeset relucky:add-email-list-initialized-to-news-table
ALTER TABLE news
    ADD COLUMN email_list_initialized BOOLEAN;