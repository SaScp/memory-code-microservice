CREATE SCHEMA IF NOT EXISTS user_microservice;

CREATE TABLE IF NOT EXISTS t_auth
(
    id SERIAL PRIMARY KEY,
    login varchar(255)  NOT NULL,
    password varchar(255)  NOT NULL,
    );

CREATE TABLE IF NOT EXISTS t_user
(
    id SERIAL PRIMARY KEY,
    user_id bigint UNIQUE,
    auth_id bigint  REFERENCES t_auth(id),
    created_at timestamp NOT NULL,
    last_activity timestamp  NOT NULL
    );