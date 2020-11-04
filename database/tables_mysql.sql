create database if not exists mystudio
  default character set utf8
  default collate utf8_general_ci;
  
create database if not exists dbnode1
  default character set utf8
  default collate utf8_general_ci;
  
create database if not exists dbnode2
  default character set utf8
  default collate utf8_general_ci;

-- 默认数据库：在mystudio数据库实例中均创建t_account
drop table if exists t_account;
create table t_account
(
   id                   bigint not null comment '主键id',
   name                 varchar(64) not null comment '账户名',
   type                 int not null comment '账户类型：1买家 2卖家',
   balance              decimal(10,2) not null comment '账户余额',
   update_time          datetime not null default CURRENT_TIMESTAMP comment '更新时间',
   primary key (id)
) engine=innodb default charset=utf8;
alter table t_account comment '账户';
  
-- 水平分库：在dbnode1 dbnode2数据库实例中均创建t_payment
drop index idx1_payment on t_payment;
drop table if exists t_payment;
create table t_payment
(
   id                   bigint not null comment '主键id',
   order_id             bigint not null comment '订单id',
   account_id           bigint not null comment '账户id',
   amount               decimal(10,2) not null comment '支付金额',
   pay_time             datetime not null comment '付款时间',
   is_refund            tinyint not null default 0 comment '是否退款：0否 1是',
   primary key (id)
) engine = innodb character set = utf8;
alter table t_payment comment '支付';
create index idx1_payment on t_payment( order_id );

-- 水平分库分表：在dbnode1 dbnode2数据库实例中均创建t_order_1、t_order_2
drop table if exists t_order_1;
create table t_order_1
(
   id                   bigint not null comment '主键id',
   price                decimal(10,2) not null comment '价格',
   account_id           bigint not null comment '账户id',
   order_time           datetime not null default CURRENT_TIMESTAMP comment '下单时间',
   status               int not null comment '状态：1订单提交 2支付成功 3取消订单',
   primary key (id)
) engine = innodb character set = utf8;
alter table t_order_1 comment '订单';

drop table if exists t_order_2;
create table t_order_2
(
   id                   bigint not null comment '主键id',
   price                decimal(10,2) not null comment '价格',
   account_id           bigint not null comment '账户id',
   order_time           datetime not null default CURRENT_TIMESTAMP comment '下单时间',
   status               int not null comment '状态：1订单提交 2支付成功 3取消订单',
   primary key (id)
) engine = innodb character set = utf8;
alter table t_order_2 comment '订单';

-- 全局广播表：在所有数据库（mystudio dbnode1 dbnode2）中都创建t_dict
drop table if exists t_dict;
create table t_dict
(
   id                   bigint not null comment '主键id',
   pid                  bigint comment '所属id',
   category             varchar(32) not null comment '类别',
   item_label           varchar(32) not null comment '标签',
   item_code            varchar(32) not null comment '编码',
   primary key (id)
) engine = innodb character set = utf8;
alter table t_dict comment '字典';
