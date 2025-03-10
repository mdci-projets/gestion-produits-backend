INSERT INTO Product (id, name, description, price) VALUES
(1, 'Product A', 'Description of Product A', 19.99),
(2, 'Product B', 'Description of Product B', 29.99),
(3, 'Product C', 'Description of Product C', 39.99),
(4, 'Product D', 'Description of Product D', 49.99),
(5, 'Product E', 'Description of Product E', 59.99);

-- Mettre à jour l'auto-incrémentation
ALTER TABLE product ALTER COLUMN id RESTART WITH 6;

INSERT INTO USERS (ID, USERNAME, PASSWORD) VALUES (1, 'youmassa', '$2y$10$3xRmg7Tmbt/rOXh9./q1bObx6tBZaNvLLcNTlt8pfN/FL8kR5Z9nK');
INSERT INTO USERS (ID, USERNAME, PASSWORD) VALUES (2, 'user', '$2y$10$3xRmg7Tmbt/rOXh9./q1bObx6tBZaNvLLcNTlt8pfN/FL8kR5Z9nK');
ALTER TABLE USERS ALTER COLUMN id RESTART WITH 3;

INSERT INTO USER_ROLES (USER_ID, ROLE) VALUES (1, 'ADMIN');
INSERT INTO USER_ROLES (USER_ID, ROLE) VALUES (2, 'USER');
