--liquibase formatted sql


--changeset vadym:1
CREATE TABLE confirmation_code
(
    id SERIAL PRIMARY KEY,
    code varchar NOT NULL UNIQUE,
    user_id int references users (user_id),
    date_time timestamp
);



