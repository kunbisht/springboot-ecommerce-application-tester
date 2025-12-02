-- Sample data for the e-commerce application
-- This file is loaded automatically by Spring Boot on startup

INSERT INTO products (name, description, price, stock_quantity, category, brand, image_url, active, created_at, updated_at) VALUES
('iPhone 15 Pro', 'Latest Apple smartphone with A17 Pro chip', 999.99, 50, 'Electronics', 'Apple', 'https://example.com/iphone15pro.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Samsung Galaxy S24', 'Premium Android smartphone with AI features', 899.99, 30, 'Electronics', 'Samsung', 'https://example.com/galaxys24.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('MacBook Air M3', 'Ultra-thin laptop with M3 chip', 1299.99, 20, 'Computers', 'Apple', 'https://example.com/macbookair.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Dell XPS 13', 'Premium ultrabook for professionals', 1199.99, 15, 'Computers', 'Dell', 'https://example.com/dellxps13.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sony WH-1000XM5', 'Noise-canceling wireless headphones', 349.99, 100, 'Audio', 'Sony', 'https://example.com/sonyheadphones.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('AirPods Pro 2', 'Apple wireless earbuds with ANC', 249.99, 75, 'Audio', 'Apple', 'https://example.com/airpodspro.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('iPad Pro 12.9"', 'Professional tablet with M2 chip', 1099.99, 25, 'Tablets', 'Apple', 'https://example.com/ipadpro.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Surface Pro 9', 'Microsoft 2-in-1 laptop tablet', 999.99, 20, 'Tablets', 'Microsoft', 'https://example.com/surfacepro9.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Nintendo Switch OLED', 'Gaming console with OLED screen', 349.99, 40, 'Gaming', 'Nintendo', 'https://example.com/switch.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('PlayStation 5', 'Next-gen gaming console', 499.99, 10, 'Gaming', 'Sony', 'https://example.com/ps5.jpg', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
