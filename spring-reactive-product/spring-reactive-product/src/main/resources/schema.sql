--DROP TABLE IF EXISTS product;


CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    price FLOAT ,
    category VARCHAR(255),
    description VARCHAR(255),
    image_url VARCHAR(255),
    stock_quantity int,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);