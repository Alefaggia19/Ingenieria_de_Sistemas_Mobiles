INSERT INTO USERS (realname,password,email)
VALUES
('admin','$2a$12$Akz1PcXyaQhFpTEWSF2A6e92PxYiPrfOihVz/NVpiteGQWJO3OQ8K','admin@checkit.com');

INSERT INTO users (realname, email, password) VALUES ('Ana Gómez', 'ana@example.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.TVuHOnu');

INSERT INTO challenges (name, description, is_ordered, user_id, creation_date) VALUES ('Ruta Romana', 'Descubre el legado romano de Córdoba.', true, 1, CURRENT_TIMESTAMP);