create table customers
(
    id   bigint auto_increment
        primary key,
    name varchar(200) not null
);

INSERT INTO webapp.customers (id, name) VALUES (1, 'Peter');
INSERT INTO webapp.customers (id, name) VALUES (2, 'Ann');
INSERT INTO webapp.customers (id, name) VALUES (3, 'Joe');
