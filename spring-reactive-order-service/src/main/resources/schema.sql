--DROP TABLE IF EXISTS orders;
--DROP TABLE IF EXISTS order_item;


CREATE TABLE IF NOT EXISTS orders(
    id SERIAL PRIMARY KEY,
    billing_address VARCHAR(255),
    customer_id INT,
    order_date DATE,
    order_status VARCHAR(255),
    payment_method VARCHAR(255),
    shipping_address VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS order_item (
    id SERIAL PRIMARY KEY,
    price FLOAT,
    product_id BIGINT,
    quantity INT,
    order_id BIGINT
);

