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

