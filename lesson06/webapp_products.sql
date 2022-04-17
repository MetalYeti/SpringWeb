create table products
(
    id    bigint auto_increment
        primary key,
    cost  bigint       null,
    title varchar(255) not null
);

INSERT INTO webapp.products (id, cost, title) VALUES (23, 200, 'Tea');
INSERT INTO webapp.products (id, cost, title) VALUES (24, 500, 'Bread');
INSERT INTO webapp.products (id, cost, title) VALUES (25, 350, 'Milk');
INSERT INTO webapp.products (id, cost, title) VALUES (26, 1000, 'Coffee');
INSERT INTO webapp.products (id, cost, title) VALUES (27, 400, 'Butter');
