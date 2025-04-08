drop database if exists dingcan;
create database dingcan charset utf8mb4;
use dingcan;

create table user
(
    id       int auto_increment primary key,
    username varchar(32) unique not null check ( length(username) > 0 ),
    password varchar(32)        not null check ( length(password) > 0 ),
    is_admin boolean            not null
);

create table dish
(
    id    int auto_increment primary key,
    name  varchar(32) unique not null check ( length(name) > 0 ),
    price double             not null check ( price >= 0 )
);

create table `order`
(
    id      int auto_increment primary key,
    user_id int not null,
    dish_id int not null,
    foreign key (user_id) references user (id),
    foreign key (dish_id) references dish (id)
);

insert into user
values (default, 'sa', 'sa', true),
       (default, 'a', 'a', false);

insert into dish
values (default, '提拉米苏', 15),
       (default, '抹茶慕斯', 12),
       (default, '蓝莓芝士', 16),
       (default, '奶油蛋糕', 13);

insert into `order`
values (default, 1, 1),
       (default, 1, 2),
       (default, 2, 3),
       (default, 2, 4);

select *
from user;

select *
from dish;

select *
from `order`;
