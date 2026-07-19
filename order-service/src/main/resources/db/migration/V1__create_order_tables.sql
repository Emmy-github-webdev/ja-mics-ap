CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    customer_email VARCHAR(255),
    total_amount DOUBLE PRECISION,
    status VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT,
    product_name VARCHAR(255),
    quantity INTEGER,
    unit_price DOUBLE PRECISION,
    order_id BIGINT NOT NULL,

    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_order_items_order_id
    ON order_items(order_id);