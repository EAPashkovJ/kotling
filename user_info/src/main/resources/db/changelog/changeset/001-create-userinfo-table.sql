--liquibase formatted sql

--author Evgenii Pashkov

CREATE TABLE UserInfoFromWorkSheet (
                                       id UUID PRIMARY KEY,
                                       firstname VARCHAR(255) NOT NULL,
                                       lastname VARCHAR(255) NOT NULL,
                                       city VARCHAR(255) NOT NULL,
                                       age INT NOT NULL
);