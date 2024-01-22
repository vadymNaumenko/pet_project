--liquibase formatted sql


--changeset vadym:1
CREATE TABLE IF NOT EXISTS ticket_orders
(
    order_id SERIAL PRIMARY KEY,
    game_id INT REFERENCES games (game_id),
    user_id INT REFERENCES users (user_id),
    price int,
    state VARCHAR(64),
    number VARCHAR(64),
    create_at timestamp
);