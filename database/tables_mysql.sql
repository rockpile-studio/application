drop index idx1_payment on t_payment;

drop table if exists t_payment;

create table t_payment (
  id			bigint not null comment '支付id',
  order_id		bigint not null comment '订单id',
  amount		decimal(10,2) not null comment '支付金额',
  remark		varchar(255) comment '备注',
  pay_time		datetime not null default CURRENT_TIMESTAMP comment '支付时间',
  is_fallback	tinyint not null default 0 comment '是否回退：0否(默认) 1是',
  primary key (id)
) engine=innodb default charset=utf8;

alter table t_payment comment '缴费信息';

create index idx1_payment on t_payment(order_id);

create database if not exists dbnode1
  default character set utf8
  default collate utf8_general_ci;
  
create database if not exists dbnode2
  default character set utf8
  default collate utf8_general_ci;

-- 全局广播表：在所有数据库（mystudio dbnode1 dbnode2）中都创建t_dict
create table t_dict (
  dict_id	bigint not null comment '字典id',
  type		varchar(64) not null comment '字典类型',
  code		varchar(64) not null comment '字典编码',
  value		varchar(64) not null comment '字典值',
  primary key (dict_id)
) engine = innodb character set = utf8;

alter table t_dict comment '字典信息';

-- 水平分库：在mystudio数据库实例中均创建t_account
create table t_account (
  account_id	bigint not null comment '账户id',
  fullname		varchar(32) not null comment '账户名',
  account_type	int default 0 not null comment '账户类型',
  primary key (account_id)
) engine = innodb character set = utf8;

alter table t_account comment '账户信息';

-- 水平分库分表：在dbnode1 dbnode2数据库实例中均创建t_order_1、t_order_2
CREATE TABLE t_order_1 (
  order_id		bigint not null comment '订单id',
  price			decimal(10,2) not null comment '价格',
  account_id	bigint not null comment '账户id',
  order_time	datetime default current_timestamp not null comment '下单时间',
  status		varchar(8) not null comment '订单状态',
  primary key (order_id)
) engine = innodb character set = utf8;

alter table t_order_1 comment '订单信息';

CREATE TABLE t_order_2 (
  order_id		bigint not null comment '订单id',
  price			decimal(10,2) not null comment '价格',
  account_id	bigint not null comment '账户id',
  order_time	datetime default current_timestamp not null comment '下单时间',
  status		varchar(8) not null comment '订单状态',
  primary key (order_id)
) engine = innodb character set = utf8;

alter table t_order_2 comment '订单信息';