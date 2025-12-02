# E-commerce Application API Documentation

## Overview

This document provides comprehensive API documentation for the Spring Boot E-commerce Application. The API follows REST principles and provides endpoints for managing products, users, and orders.

## Base URL

```
Local Development: http://localhost:8080
Production: https://your-domain.com
```

## Authentication

The API uses JWT (JSON Web Tokens) for authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## API Endpoints

### Health Check

#### GET /api/health

Check the application health status.

**Response:**
```json
{
  "status": "UP",
  "timestamp": "2024-01-01T12:00:00",
  "service": "ecommerce-application",
  "version": "1.0.0"
}
```

#### GET /api/health/ready

Readiness probe endpoint.

**Response:**
```json
{
  "status": "READY"
}
```

#### GET /api/health/live

Liveness probe endpoint.

**Response:**
```json
{
  "status": "ALIVE"
}
```

### Products

#### GET /api/v1/products

Retrieve all products with pagination.

**Query Parameters:**
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 20)
- `sort` (optional): Sort field and direction (e.g., `name,asc`)

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "name": "iPhone 15 Pro",
      "description": "Latest Apple smartphone",
      "price": 999.99,
      "stockQuantity": 50,
      "category": "Electronics",
      "brand": "Apple",
      "imageUrl": "https://example.com/image.jpg",
      "active": true,
      "createdAt": "2024-01-01T12:00:00",
      "updatedAt": "2024-01-01T12:00:00"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true
    },
    "pageNumber": 0,
    "pageSize": 20
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "first": true,
  "numberOfElements": 1
}
```

#### GET /api/v1/products/{id}

Retrieve a specific product by ID.

**Path Parameters:**
- `id`: Product ID (required)

**Response:**
```json
{
  "id": 1,
  "name": "iPhone 15 Pro",
  "description": "Latest Apple smartphone",
  "price": 999.99,
  "stockQuantity": 50,
  "category": "Electronics",
  "brand": "Apple",
  "imageUrl": "https://example.com/image.jpg",
  "active": true,
  "createdAt": "2024-01-01T12:00:00",
  "updatedAt": "2024-01-01T12:00:00"
}
```

#### POST /api/v1/products

Create a new product. **Requires ADMIN role.**

**Request Body:**
```json
{
  "name": "New Product",
  "description": "Product description",
  "price": 199.99,
  "stockQuantity": 100,
  "category": "Electronics",
  "brand": "BrandName",
  "imageUrl": "https://example.com/image.jpg"
}
```

**Response:** Same as GET /api/v1/products/{id}

#### PUT /api/v1/products/{id}

Update an existing product. **Requires ADMIN role.**

**Path Parameters:**
- `id`: Product ID (required)

**Request Body:** Same as POST request

**Response:** Updated product object

#### DELETE /api/v1/products/{id}

Delete a product. **Requires ADMIN role.**

**Path Parameters:**
- `id`: Product ID (required)

**Response:** 204 No Content

#### GET /api/v1/products/search

Search products by name.

**Query Parameters:**
- `name`: Product name to search for (required)
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 20)

**Response:** Same format as GET /api/v1/products

## Error Responses

The API returns consistent error responses:

### 400 Bad Request
```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/products",
  "fieldErrors": {
    "name": "Product name is required",
    "price": "Price must be greater than 0"
  }
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found with ID 123",
  "path": "/api/v1/products/123"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/v1/products"
}
```

## Rate Limiting

API endpoints are rate-limited to prevent abuse:
- **Public endpoints**: 100 requests per minute per IP
- **Authenticated endpoints**: 1000 requests per minute per user
- **Admin endpoints**: 500 requests per minute per user

## Monitoring and Metrics

Actuator endpoints are available for monitoring:

- `/actuator/health` - Application health
- `/actuator/info` - Application information
- `/actuator/metrics` - Application metrics
- `/actuator/prometheus` - Prometheus metrics

## SDKs and Examples

### cURL Examples

```bash
# Get all products
curl -X GET "http://localhost:8080/api/v1/products?page=0&size=10"

# Get product by ID
curl -X GET "http://localhost:8080/api/v1/products/1"

# Create product (requires authentication)
curl -X POST "http://localhost:8080/api/v1/products" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "name": "New Product",
    "description": "Product description",
    "price": 199.99,
    "stockQuantity": 100,
    "category": "Electronics",
    "brand": "BrandName"
  }'

# Search products
curl -X GET "http://localhost:8080/api/v1/products/search?name=iPhone"
```

### JavaScript/Node.js Example

```javascript
// Get all products
const response = await fetch('http://localhost:8080/api/v1/products');
const products = await response.json();

// Create product
const newProduct = {
  name: 'New Product',
  description: 'Product description',
  price: 199.99,
  stockQuantity: 100,
  category: 'Electronics',
  brand: 'BrandName'
};

const createResponse = await fetch('http://localhost:8080/api/v1/products', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  },
  body: JSON.stringify(newProduct)
});

const createdProduct = await createResponse.json();
```

## Changelog

### Version 1.0.0
- Initial API release
- Product management endpoints
- Health check endpoints
- Authentication and authorization
- Comprehensive error handling
- API documentation

## Support

For API support and questions:
- Email: api-support@ecommerce.com
- Documentation: [API Docs](https://docs.ecommerce.com)
- GitHub Issues: [Repository Issues](https://github.com/kunbisht/springboot-ecommerce-application-tester/issues)
