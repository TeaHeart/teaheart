DROP TABLE IF EXISTS tb_user;

CREATE TABLE tb_user
(
    id       bigint(20) NOT NULL COMMENT 'ID',
    username varchar(200) DEFAULT NULL COMMENT '姓名',
    status   int(2) DEFAULT '1' COMMENT '1: 未启用, 2: 已启用, 3: 已关闭',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


insert into tb_user (id, username, status)
values (1, 'Tom', 1);
insert into tb_user (id, username, status)
values (2, 'Cat', 2);
insert into tb_user (id, username, status)
values (3, 'Rose', 3);
insert into tb_user (id, username, status)
values (4, 'Coco', 2);
insert into tb_user (id, username, status)
values (5, 'Lily', 1);
insert into tb_user (id, username, status)
values (6, 'Tom', 1);
insert into tb_user (id, username, status)
values (7, 'Cat', 2);
insert into tb_user (id, username, status)
values (8, 'Rose', 3);
insert into tb_user (id, username, status)
values (9, 'Coco', 2);
insert into tb_user (id, username, status)
values (10, 'Lily', 1);