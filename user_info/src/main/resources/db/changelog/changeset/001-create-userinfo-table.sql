--liquibase formatted sql
--author Evgenii Pashkov
-- Date: 28/04/2023

CREATE TABLE user_info (
                           id BIGSERIAL NOT NULL PRIMARY KEY,
                           firstname VARCHAR(50) NOT NULL,
                           lastname VARCHAR(50) NOT NULL,
                           city VARCHAR(50) NOT NULL,
                           age INT NOT NULL
);