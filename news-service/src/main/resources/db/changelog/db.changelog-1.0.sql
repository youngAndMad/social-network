--liquibase formatted sql;
--changeset relucky:create-news-table
CREATE TABLE news
(
    id      BIGSERIAL PRIMARY KEY,
    content VARCHAR(255),
    title   VARCHAR(255)
);
--rollback drop table news;

--changeset relucky:create-news_file_url-table
CREATE TABLE file_meta_data
(
    id      BIGSERIAL PRIMARY KEY,
    news_id  BIGINT NOT NULL,
    url VARCHAR(255),
    file_id VARCHAR(255),
    FOREIGN KEY (news_id) REFERENCES news(id)
);
--rollback drop table news_file_url;
