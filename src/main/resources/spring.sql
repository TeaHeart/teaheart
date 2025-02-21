drop database if exists spring;

create database spring;

use spring;

create table
user (
    id int primary key auto_increment,
    username varchar(32) unique not null,
    password varchar(32) not null
);

insert into user (username, password) values ('sa', 'sa');

select * from user;