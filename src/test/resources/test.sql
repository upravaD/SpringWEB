CREATE TABLE IF NOT EXISTS roles (id SERIAL PRIMARY KEY,
                    role_name VARCHAR(50) NOT NULL);


CREATE TABLE IF NOT EXISTS permissions (id SERIAL PRIMARY KEY,
                          permission_name VARCHAR(50) NOT NULL);


CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY,
                    username VARCHAR(50) NOT NULL,
                    role_id INT,
                    FOREIGN KEY (role_id) REFERENCES roles(id));


CREATE TABLE IF NOT EXISTS role_permissions (role_id INT,
                               permission_id INT,
                               PRIMARY KEY (role_id, permission_id),
                               FOREIGN KEY (role_id) REFERENCES roles(id),
                               FOREIGN KEY (permission_id) REFERENCES permissions(id));