drop
database if exists blockchain;
create
database blockchain;

use
blockchain;

create table account
(
    id       int auto_increment primary key not null comment '编号',
    username varchar(32) unique                           not null comment '用户名',
    password varchar(32)                                  not null comment '密码',
    token    char(42) unique check ( length(token) = 42 ) not null comment '以太坊token'
) comment '账户表';

create table item_info
(
    id          int auto_increment primary key not null comment '编号',
    name        varchar(32) unique not null comment '名称',
    description text comment '描述'
) comment '物品信息表';

truncate table item_info;

create table item_repository
(
    id           int auto_increment primary key not null comment '编号',
    account_id   int                                          not null comment '玩家编号',
    item_info_id int                                          not null comment '物品信息编号',
    token        char(42) unique check ( length(token) = 42 ) not null comment '物品token',
    is_available bit                                          not null default true comment '是否可用',
    foreign key (account_id) references account (id),
    foreign key (item_info_id) references item_info (id)
) comment '物品存储表';

create index idx_item_repository_all on item_repository (account_id, item_info_id, is_available, token);

show
index from item_repository;

select *
from account;

select *
from item_info;

select *
from item_repository;

-- 本地测试数据
insert into account (username, password, token)
values ('zhang3', '', '0x0000000000000000000000000000000000000001'),
       ('li4', '', '0x0000000000000000000000000000000000000002');
insert into item_info (name, description)
values ('小袋金币', '使用后获得 100 金币'),
       ('大袋金币', '使用后获得 500 金币');

insert into item_repository (account_id, item_info_id, token)
values (1, 1, '0x0000000000000000000000000000000000000001'),
       (1, 2, '0x0000000000000000000000000000000000000002'),
       (2, 1, '0x0000000000000000000000000000000000000003'),
       (2, 2, '0x0000000000000000000000000000000000000004');

explain
select ir.id    as id,
       account_id,
       username,
       password,
       a.token  as atoken,
       item_info_id,
       name,
       description,
       ir.token as token,
       is_available
from item_repository as ir
         inner join account a on ir.account_id = a.id
         inner join item_info i on ir.item_info_id = i.id;

select username as '所有者', a.token as '用户token', name as '道具名称', ir.token as '物品token', is_available as '是否可用'
from item_repository as ir
         inner join account a on ir.account_id = a.id
         inner join item_info i on ir.item_info_id = i.id
order by ir.id;
