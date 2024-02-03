--liquibase formatted sql


--changeset vadym:1
CREATE TABLE forgot_password
   (
       id SERIAL PRIMARY KEY,
       user_id BIGINT REFERENCES users (user_id),
       code varchar,
       active boolean,
       created_at timestamp
);

