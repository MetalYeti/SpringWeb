create table products_customers
(
    customer_id bigint not null,
    product_id  bigint null,
    constraint products_customers_customers_id_fk
        foreign key (customer_id) references customers (id),
    constraint products_customers_products_id_fk
        foreign key (product_id) references products (id)
);

INSERT INTO webapp.products_customers (customer_id, product_id) VALUES (1, 23);
INSERT INTO webapp.products_customers (customer_id, product_id) VALUES (1, 24);
INSERT INTO webapp.products_customers (customer_id, product_id) VALUES (1, 27);
INSERT INTO webapp.products_customers (customer_id, product_id) VALUES (2, 26);
INSERT INTO webapp.products_customers (customer_id, product_id) VALUES (2, 25);
INSERT INTO webapp.products_customers (customer_id, product_id) VALUES (3, 24);
INSERT INTO webapp.products_customers (customer_id, product_id) VALUES (3, 25);
INSERT INTO webapp.products_customers (customer_id, product_id) VALUES (3, 27);
