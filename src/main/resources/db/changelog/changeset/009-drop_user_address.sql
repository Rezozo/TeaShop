DROP TABLE user_address;

ALTER TABLE address
ADD COLUMN user_id bigint NOT NULL REFERENCES users (id)