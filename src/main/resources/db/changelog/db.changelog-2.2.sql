--liquibase formatted sql


--changeset vadym:1
CREATE TABLE comment_on_news
(
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT REFERENCES news (id),
    user_id BIGINT REFERENCES users (user_id),
    comment_text text,
    created_at timestamp
);

--changeset vadym:2
CREATE TABLE reaction_to_news_comment
(
    id BIGSERIAL PRIMARY KEY,
    commit_id BIGINT REFERENCES comment_on_news (id),
    user_id BIGINT REFERENCES users (user_id),
    reaction varchar(10),
    created_at timestamp
);


