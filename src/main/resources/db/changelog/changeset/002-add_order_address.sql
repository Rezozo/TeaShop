ALTER TABLE orders
ADD COLUMN address_id bigint NOT NULL REFERENCES address (id)