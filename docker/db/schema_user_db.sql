CREATE SCHEMA IF NOT EXISTS user_microservice;

CREATE TABLE IF NOT EXISTS user_microservice.t_auth
(
    id SERIAL PRIMARY KEY,
    login varchar(255) UNIQUE,
    password varchar(255)  NOT NULL
    );

CREATE TABLE IF NOT EXISTS user_microservice.t_user
(
    id SERIAL PRIMARY KEY,
    user_id bigint UNIQUE,
    auth_id bigint  REFERENCES user_microservice.t_auth(id) ON DELETE SET NULL,
    first_lang_set boolean DEFAULT FALSE,
    created_at timestamp NOT NULL,
    last_activity timestamp  NOT NULL,
    lang_code varchar(255)  NOT NULL
    );
