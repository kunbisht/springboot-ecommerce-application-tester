-- Database initialization script for PostgreSQL
-- This script sets up the initial database schema and sample data

-- Create database if it doesn't exist (handled by Docker environment variables)
-- CREATE DATABASE IF NOT EXISTS ecommerce;

-- Use the ecommerce database
\c ecommerce;

-- Create products table
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL CHECK (price > 0),
    stock_quantity INTEGER NOT NULL CHECK (stock_quantity >= 0),
    category VARCHAR(100),
    brand VARCHAR(100),
    image_url TEXT,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_products_category ON products(category);
CREATE INDEX IF NOT EXISTS idx_products_brand ON products(brand);
CREATE INDEX IF NOT EXISTS idx_products_active ON products(active);
CREATE INDEX IF NOT EXISTS idx_products_name ON products(name);
CREATE INDEX IF NOT EXISTS idx_products_price ON products(price);

-- Create updated_at trigger function
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create trigger for products table
DROP TRIGGER IF EXISTS update_products_updated_at ON products;
CREATE TRIGGER update_products_updated_at
    BEFORE UPDATE ON products
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Insert sample data
INSERT INTO products (name, description, price, stock_quantity, category, brand, image_url, active) VALUES
('iPhone 15 Pro', 'Latest Apple smartphone with A17 Pro chip and titanium design', 999.99, 50, 'Electronics', 'Apple', 'https://example.com/iphone15pro.jpg', true),
('Samsung Galaxy S24 Ultra', 'Premium Android smartphone with S Pen and AI features', 1199.99, 30, 'Electronics', 'Samsung', 'https://example.com/galaxys24ultra.jpg', true),
('MacBook Air M3', 'Ultra-thin laptop with M3 chip and all-day battery life', 1299.99, 20, 'Computers', 'Apple', 'https://example.com/macbookair.jpg', true),
('Dell XPS 13 Plus', 'Premium ultrabook with InfinityEdge display', 1199.99, 15, 'Computers', 'Dell', 'https://example.com/dellxps13.jpg', true),
('Sony WH-1000XM5', 'Industry-leading noise-canceling wireless headphones', 349.99, 100, 'Audio', 'Sony', 'https://example.com/sonyheadphones.jpg', true),
('AirPods Pro 2nd Gen', 'Apple wireless earbuds with active noise cancellation', 249.99, 75, 'Audio', 'Apple', 'https://example.com/airpodspro.jpg', true),
('iPad Pro 12.9" M2', 'Professional tablet with M2 chip and Liquid Retina XDR display', 1099.99, 25, 'Tablets', 'Apple', 'https://example.com/ipadpro.jpg', true),
('Surface Pro 9', 'Microsoft 2-in-1 laptop tablet with Intel Core processors', 999.99, 20, 'Tablets', 'Microsoft', 'https://example.com/surfacepro9.jpg', true),
('Nintendo Switch OLED', 'Gaming console with vibrant OLED screen', 349.99, 40, 'Gaming', 'Nintendo', 'https://example.com/switch.jpg', true),
('PlayStation 5', 'Next-generation gaming console with 4K gaming', 499.99, 10, 'Gaming', 'Sony', 'https://example.com/ps5.jpg', true),
('Xbox Series X', 'Most powerful Xbox console with 4K gaming at 120fps', 499.99, 12, 'Gaming', 'Microsoft', 'https://example.com/xboxseriesx.jpg', true),
('Apple Watch Series 9', 'Advanced smartwatch with health monitoring', 399.99, 60, 'Wearables', 'Apple', 'https://example.com/applewatch.jpg', true),
('Samsung Galaxy Watch 6', 'Smart watch with comprehensive health tracking', 329.99, 45, 'Wearables', 'Samsung', 'https://example.com/galaxywatch.jpg', true),
('Google Pixel 8 Pro', 'AI-powered Android smartphone with advanced camera', 899.99, 35, 'Electronics', 'Google', 'https://example.com/pixel8pro.jpg', true),
('OnePlus 12', 'Flagship Android phone with fast charging', 799.99, 25, 'Electronics', 'OnePlus', 'https://example.com/oneplus12.jpg', true);

-- Create users table for future authentication
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for users table
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);

-- Create trigger for users table
DROP TRIGGER IF EXISTS update_users_updated_at ON users;
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Insert default admin user (password: admin123)
INSERT INTO users (username, email, password_hash, first_name, last_name, role) VALUES
('admin', 'admin@ecommerce.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFGjO6NaaesAR1lzMOKR3Ri', 'Admin', 'User', 'ADMIN'),
('user', 'user@ecommerce.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iYqiSfFGjO6NaaesAR1lzMOKR3Ri', 'Test', 'User', 'USER');

-- Grant necessary permissions
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO ecommerce;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO ecommerce;

-- Display success message
\echo 'Database initialization completed successfully!';
