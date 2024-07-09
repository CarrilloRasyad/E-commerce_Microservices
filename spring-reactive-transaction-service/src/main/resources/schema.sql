--DROP TABLE IF EXISTS transaction_details:
--DROP TABLE IF EXISTS customer_balance;

CREATE TABLE IF NOT EXISTS transaction_details(
    id SERIAL PRIMARY KEY,
    amount FLOAT,
    order_id BIGINT,
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    mode VARCHAR(255),
    status VARCHAR(255),
    reference_number VARCHAR(255));


CREATE TABLE IF NOT EXISTS customer_balance(
    id SERIAL PRIMARY KEY,
    customer_id BIGINT,
    amount float);