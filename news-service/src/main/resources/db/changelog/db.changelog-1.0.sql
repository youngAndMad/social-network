--changeset relucky:1
CREATE TABLE IF NOT EXISTS news
(
    id      BIGSERIAL PRIMARY KEY,
    content VARCHAR(255),
    title   VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS news_file_url
(
    news_id  BIGINT NOT NULL,
    file_url VARCHAR(255),
    FOREIGN KEY (news_id) REFERENCES news(id)
);