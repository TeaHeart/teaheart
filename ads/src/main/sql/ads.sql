drop database if exists ads;
create database ads charset utf8mb4;
use ads;

create table user
(
    id          int auto_increment primary key,
    role     enum ('农民', '技术员', '管理员') not null,
    username varchar(32) unique                not null check ( length(username) > 0 ),
    password varchar(32)                       not null check ( length(password) > 0 ),
    description varchar(255)
);

create table node
(
    id          int auto_increment primary key,
    type    enum ('Sensor-A', 'Sensor-B', 'Sensor-C') not null,
    address varchar(32) unique                        not null,
    command varchar(64)                               not null,
    description varchar(255)
);

create table history
(
    id      int auto_increment primary key,
    node_id int                                 not null,
    time    timestamp default current_timestamp not null,
    data    varchar(255)                        not null,
    foreign key (node_id) references node (id)
);

create table policy
(
    id          int auto_increment primary key,
    source_id   int                   not null,
    target_id   int                   not null,
    enabled     boolean default false not null,
    expression varchar(32) not null,
    command    varchar(64) not null,
    description varchar(255),
    foreign key (source_id) references node (id),
    foreign key (target_id) references node (id)
);

create table system_config
(
    id          int auto_increment primary key,
    `key`       varchar(255) unique not null,
    value       varchar(255)        not null,
    description varchar(255)
);

insert into user (role, username, password, description)
values ('管理员', 'a', 'a', '默认-管理员'),
       ('技术员', 't', 't', '默认-技术员'),
       ('农民', 'f', 'f', '默认-农民');

insert into node (type, address, command, description)
values #
       ('Sensor-A', '01:12:4B:00:7C:67:CE:45', '{A0=?,A1=?,A2=?,A3=?,A4=?,A5=?,A6=?,D1=?}', '仿真-SensorA'),
       ('Sensor-B', '01:12:4B:00:45:75:29:4D', '{D1=?}', '仿真-SensorB'),
       ('Sensor-A', '00:12:4B:00:2A:5D:07:1B', '{A0=?,A1=?,A2=?,A3=?,A4=?,A5=?,A6=?,D1=?}', '4号箱-SensorA'),
       ('Sensor-B', '00:12:4B:00:2A:5D:04:B2', '{D1=?}', '4号箱-SensorB');

insert into history (node_id, time, data)
values #
       (1, '2024-01-01T00:00:00', '{A2=3200}'),
       (1, '2024-02-01T00:00:00', '{A2=6400}'),
       (2, '2024-01-01T00:00:00', '{D1=20}'),
       (2, '2024-02-01T00:00:00', '{D1=4}');

insert into policy (source_id, target_id, enabled, expression, command, description)
values #
       (1, 2, false, 'true', '{OD1=4,D1=?}', '打开遮阳帘'),
       (1, 2, false, 'true', '{CD1=4,D1=?}', '关闭遮阳帘'),
       (1, 2, false, 'true', '{OD1=16,D1=?}', '打开补光灯'),
       (1, 2, false, 'true', '{CD1=16,D1=?}', '关闭补光灯'),
       (1, 2, true, 'A2>1000', '{CD1=4,D1=?}', '光照大于1000,关闭遮阳帘'),
       (1, 2, true, 'A2<700', '{OD1=4,D1=?}', '光照小于700,打开遮阳帘'),
       (1, 2, true, 'A2<100', '{OD1=16,D1=?}', '光照小于100,打开补光灯'),
       (1, 2, true, 'A2>300', '{CD1=16,D1=?}', '光照大于300,关闭补光灯');

insert into system_config (`key`, value, description)
values #
       ('ads.zcloud.socket-server', 'ws://api.zhiyun360.com:28080', '用于获取/控制设备'),
       ('ads.zcloud.web-server', 'http://api.zhiyun360.com:8080', '用于获取历史数据'),
       ('ads.zcloud.uid', '737473223192', '账号'),
       ('ads.zcloud.key', 'DwtWUVNXBlcECAsFYFpZUFhEQTo', '密钥'),
       ('ads.zcloud.update-internal', '3000', '自动更新/控制的间隔时间(毫秒)');

select *
from user;
select *
from node;
select *
from history;
select *
from policy;
select *
from system_config;
