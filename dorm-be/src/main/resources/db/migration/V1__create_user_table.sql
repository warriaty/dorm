create table users
(
    id       bigint generated by default as identity (start with 1) primary key,
    email    varchar(50)  not null UNIQUE,
    password varchar(100) not null
);