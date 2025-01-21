INSERT INTO Product (id, name, description, price) VALUES
(1, 'Product A', 'Description of Product A', 19.99),
(2, 'Product B', 'Description of Product B', 29.99),
(3, 'Product C', 'Description of Product C', 39.99),
(4, 'Product D', 'Description of Product D', 49.99),
(5, 'Product E', 'Description of Product E', 59.99);

-- Mettre à jour l'auto-incrémentation
ALTER TABLE product ALTER COLUMN id RESTART WITH 6;