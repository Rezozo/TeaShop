CREATE TABLE IF NOT EXISTS role (
    id serial PRIMARY KEY,
    title varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS address (
    id bigserial PRIMARY KEY,
    city varchar(200) NOT NULL,
    address varchar(200) NOT NULL,
    flat smallint NOT NULL,
    floor smallint,
    entrance smallint,
    intercom_code varchar(20)
);

CREATE TABLE IF NOT EXISTS users (
    id bigserial PRIMARY KEY,
    role_id integer NOT NULL REFERENCES role(id) ON DELETE CASCADE,
    name varchar(100) NOT NULL,
    surname varchar(255),
    email varchar(255) NOT NULL,
    tea_bonuses integer NOT NULL,
    created_date timestamp NOT NULL,
    password text NOT NULL,
    deleted bool NOT NULL
);

CREATE TABLE IF NOT EXISTS user_address (
    user_id bigint NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    address_id bigint NOT NULL REFERENCES address(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS image (
    id bigserial PRIMARY KEY,
    image bytea NOT NULL
);

CREATE TABLE IF NOT EXISTS category (
    id serial PRIMARY KEY,
    image_id bigint NOT NULL UNIQUE REFERENCES image(id) ON DELETE CASCADE,
    name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
    id bigserial PRIMARY KEY,
    category_id integer NOT NULL REFERENCES category(id) ON DELETE CASCADE,
    article varchar(50) NOT NULL,
    title varchar(50) NOT NULL,
    description text NOT NULL,
    discount smallint
);

CREATE TABLE IF NOT EXISTS product_image (
    product_id bigint NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    image_id bigint NOT NULL UNIQUE REFERENCES image(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS variant (
    id serial PRIMARY KEY,
    title varchar(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS package (
    id bigserial PRIMARY KEY,
    variant_id integer NOT NULL REFERENCES variant(id) ON DELETE CASCADE,
    product_id bigint NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    quantity integer NOT NULL,
    price money NOT NULL
);

CREATE TABLE IF NOT EXISTS review (
    id bigserial PRIMARY KEY,
    user_id bigint NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    product_id bigint NOT NULL REFERENCES product(id) ON DELETE CASCADE,
    created_date timestamp NOT NULL,
    review_text varchar(300) NOT NULL,
    stars smallint NOT NULL
);

CREATE TABLE IF NOT EXISTS review_image (
    review_id bigint NOT NULL REFERENCES review(id) ON DELETE CASCADE,
    image_id bigint NOT NULL UNIQUE REFERENCES image(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS favorite (
    id bigserial PRIMARY KEY,
    user_id bigint NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    product_id bigint NOT NULL REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS bucket (
    id bigserial PRIMARY KEY,
    user_id bigint NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    last_modified_date timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS package_bucket (
    package_id bigint NOT NULL REFERENCES package(id) ON DELETE CASCADE,
    bucket_id bigint NOT NULL REFERENCES bucket(id) ON DELETE CASCADE,
    quantity integer NOT NULL
);

CREATE TABLE IF NOT EXISTS order_status (
    id serial PRIMARY KEY,
    status varchar(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
    id bigserial PRIMARY KEY,
    status_id integer NOT NULL REFERENCES order_status(id) ON DELETE CASCADE,
    user_id bigint NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_date timestamp NOT NULL,
    last_modified_date timestamp NOT NULL,
    tea_bonuses_spent integer,
    tea_bonuses_accrued integer,
    track_number varchar(50)
);

CREATE TABLE IF NOT EXISTS package_order (
    package_id bigint NOT NULL REFERENCES package(id) ON DELETE CASCADE,
    order_id bigint NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    quantity integer NOT NULL
);
