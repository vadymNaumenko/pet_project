--liquibase formatted sql


--changeset vadym:1
ALTER TABLE comment_on_news
ADD COLUMN is_deleted boolean



