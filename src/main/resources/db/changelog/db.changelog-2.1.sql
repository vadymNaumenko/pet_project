--liquibase formatted sql


--changeset vadym:1
CREATE TABLE events
(
    id BIGSERIAL PRIMARY KEY,
    title varchar(250),
    image_url varchar(250),
    text text,
    date DATE
);



