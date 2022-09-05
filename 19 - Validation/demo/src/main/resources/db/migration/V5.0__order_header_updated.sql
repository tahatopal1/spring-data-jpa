alter table order_header
    add column last_modified_date timestamp;

alter table product
    add column last_modified_date timestamp;