--liquibase formatted sql


--changeset vadym:1
CREATE TABLE commit_for_post
(
    id BIGSERIAL PRIMARY KEY,
    post_id BIGINT REFERENCES events (id),
    user_id BIGINT REFERENCES users (user_id),
    comment_text text,
    created_at timestamp
);

--changeset vadym:2
CREATE TABLE reaction_to_post_commit
(
    id BIGSERIAL PRIMARY KEY,
    commit_id BIGINT REFERENCES commit_for_post (id),
    user_id BIGINT REFERENCES users (user_id),
    reaction varchar(10),
    created_at timestamp
);


