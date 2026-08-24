CREATE TABLE payment_transactions (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT,
    amount DOUBLE PRECISION,
    currency VARCHAR(255),
    status VARCHAR(255),
    processed_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_payment_transactions_order_id
    ON payment_transactions(order_id);