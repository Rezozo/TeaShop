CREATE TABLE recipient (
    id bigserial PRIMARY KEY,
    name varchar(100) NOT NULL,
    surname varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    phone_number varchar(18) NOT NULL
);

ALTER TABLE orders
DROP COLUMN user_id;

ALTER TABLE orders
ADD COLUMN recipient_id bigint NOT NULL REFERENCES recipient(id);

ALTER TABLE address
ADD COLUMN active bool NOT NULL;