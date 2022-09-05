drop table if exists order_header cascade;
drop table if exists product cascade;

create table order_header
(
    id        bigint not null auto_increment primary key,
    customer      varchar(255)
) engine = InnoDB;

create table product
(
    id        bigint not null auto_increment primary key,
    description      varchar(255),
    product_status   varchar(30)
) engine = InnoDB;