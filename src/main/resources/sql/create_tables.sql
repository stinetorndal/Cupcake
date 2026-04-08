DROP TABLE IF EXISTS public.order_lines;
DROP TABLE IF EXISTS public.orders;
DROP TABLE IF EXISTS public.users;
DROP TABLE IF EXISTS public.top;
DROP TABLE IF EXISTS public.bottom;
DROP TABLE IF EXISTS public.store;

CREATE TABLE public.store
(
    store_id   SERIAL PRIMARY KEY,
    store_name VARCHAR(255) NOT NULL,
    phone      VARCHAR(25)  NOT NULL,
    email      VARCHAR(255) NOT NULL
);
CREATE TABLE public.users
(
    users_id   SERIAL PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    username   VARCHAR(50)  NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(50)  NOT NULL,
    role       VARCHAR(50)  NOT NULL,
    balance    DECIMAL(10, 2) DEFAULT 0.00
);
CREATE TABLE public.top
(
    topping_id SERIAL PRIMARY KEY,
    name       VARCHAR(255)   NOT NULL,
    price      DECIMAL(10, 2) NOT NULL
);
CREATE TABLE public.bottom
(
    bottom_id SERIAL PRIMARY KEY,
    name      VARCHAR(255)   NOT NULL,
    price     DECIMAL(10, 2) NOT NULL
);
CREATE TABLE public.orders
(
    order_id   SERIAL PRIMARY KEY,
    users_id   INT REFERENCES cupcake.users (users_id),
    created_at TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    status     VARCHAR(50) NOT NULL DEFAULT 'pending'
);
CREATE TABLE public.order_lines
(
    line_id    SERIAL PRIMARY KEY,
    order_id   INT REFERENCES cupcake.orders (order_id) ON DELETE CASCADE,
    topping_id INT REFERENCES cupcake.top (topping_id),
    bottom_id  INT REFERENCES cupcake.bottom (bottom_id),
    quantity   INT            NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    discount   INT DEFAULT 0
);
