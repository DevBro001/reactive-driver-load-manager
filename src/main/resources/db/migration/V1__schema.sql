CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       name VARCHAR(50) NOT NULL,
                       surname VARCHAR(50) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP,
                       created_by INT,
                       updated_by INT
);

CREATE TABLE drivers (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(50) NOT NULL,
                         surname VARCHAR(50) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         phone VARCHAR(15) NOT NULL,
                         user_id INT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP ,
                         created_by INT,
                         updated_by INT,
                         FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE loads (
                       id SERIAL PRIMARY KEY,
                       load_number VARCHAR(50) NOT NULL UNIQUE,
                       pick_up_address VARCHAR(255) NOT NULL,
                       delivery_address VARCHAR(255) NOT NULL,
                       driver_id INT NOT NULL,
                       status VARCHAR(20) NOT NULL,
                       user_id INT NOT NULL,
                       created_at TIMESTAMP,
                       updated_at TIMESTAMP ,
                       created_by INT,
                       updated_by INT,
                       FOREIGN KEY (driver_id) REFERENCES drivers(id) ,
                       FOREIGN KEY (user_id) REFERENCES users(id)
);
