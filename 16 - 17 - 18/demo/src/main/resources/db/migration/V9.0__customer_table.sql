create table customer (
                          id bigint not null auto_increment primary key,
                          name varchar(30),
                          created_date timestamp,
                          last_modified_date timestamp,
                          address varchar(100),
                          city varchar(30),
                          state varchar(30),
                          zip_code varchar(30),
                          phone varchar(30),
                          email varchar(30)
);

alter table order_header drop column customer;
alter table order_header add column customer_id bigint;
alter table order_header add constraint order_header_customer_fk foreign key (customer_id) references customer(id);

insert into customer (name, created_date, last_modified_date, address, city, state, zip_code, phone, email)
values ('New Customer', now(), now(), 'New Address', 'Sample City', 'Sample State', '0000', '00000000', 'customer@mail.co');

insert into customer (name, created_date, last_modified_date, address, city, state, zip_code, phone, email)
values ('New Customer 2', now(), now(), 'New Address 2', 'Sample City 2', 'Sample State 2', '0001', '00000001', 'customer2@mail.co');

insert into customer (name, created_date, last_modified_date, address, city, state, zip_code, phone, email)
values ('New Customer 3', now(), now(), 'New Address 3', 'Sample City 3', 'Sample State 3', '0003', '00000003', 'customer3@mail.co');
