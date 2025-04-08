drop database if exists ims;
create database ims charset utf8mb4;
use ims;

create table user
(
    id        int auto_increment primary key,
    username  varchar(32) unique not null check ( length(username) > 0 ),
    password  varchar(32)        not null check ( length(password) > 0 ),
    privilege int                not null check ( privilege >= 0 )
);

create table goods
(
    id   int auto_increment primary key,
    name varchar(32) unique not null check ( length(name) > 0 )
);

create table stock
(
    id     int auto_increment primary key,
    gid    int    not null,
    price  double not null check ( price >= 0 ),
    amount int    not null check ( amount >= 0 ),
    foreign key (gid) references goods (id)
);

insert into user
values (default, 'sa', 'sa', 0xFFFF);

select *
from user;

select *
from goods ;

select *
from stock;
