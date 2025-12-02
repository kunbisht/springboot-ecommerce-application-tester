# Deployment Guide

## Overview

This guide provides comprehensive instructions for deploying the Spring Boot E-commerce Application across different environments and platforms.

## Prerequisites

- Docker and Docker Compose
- Java 17+ (for local builds)
- Maven 3.9+ (for local builds)
- Kubernetes cluster (for K8s deployment)
- PostgreSQL 15+ (for production)

## Environment Configurations

### Development Environment

#### Local Development with H2

1. **Clone the repository:**
```bash
git clone https://github.com/kunbisht/springboot-ecommerce-application-tester.git
cd springboot-ecommerce-application-tester
```

2. **Run the application:**
```bash
mvn spring-boot:run
```

3. **Access the application:**
- Application: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- Health Check: http://localhost:8080/api/health
- API Documentation: http://localhost:8080/swagger-ui.html

#### Local Development with Docker Compose

1. **Start the services:**
```bash
docker-compose up -d
```

2. **Check service status:**
```bash
docker-compose ps
```

3. **View logs:**
```bash
docker-compose logs -f ecommerce-app
```

4. **Stop the services:**
```bash
docker-compose down
```

### Production Environment

#### Docker Production Deployment

1. **Build production image:**
```bash
docker build -t ecommerce-app:latest .
```

2. **Run with production profile:**
```bash
docker run -d \
  --name ecommerce-app \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/ecommerce \
  -e SPRING_DATASOURCE_USERNAME=ecommerce \
  -e SPRING_DATASOURCE_PASSWORD=your-secure-password \
  -e JAVA_OPTS="-Xmx1g -Xms512m" \
  ecommerce-app:latest
```

#### Kubernetes Deployment

1. **Create namespace:**
```yaml
# k8s/namespace.yaml
apiVersion: v1
kind: Namespace
metadata:
  name: ecommerce
```

2. **Create ConfigMap:**
```yaml
# k8s/configmap.yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: ecommerce-config
  namespace: ecommerce
data:
  SPRING_PROFILES_ACTIVE: "prod"
  JAVA_OPTS: "-Xmx1g -Xms512m"
```

3. **Create Secret:**
```yaml
# k8s/secret.yaml
apiVersion: v1
kind: Secret
metadata:
  name: ecommerce-secrets
  namespace: ecommerce
type: Opaque
data:
  SPRING_DATASOURCE_URL: <base64-encoded-url>
  SPRING_DATASOURCE_USERNAME: <base64-encoded-username>
  SPRING_DATASOURCE_PASSWORD: <base64-encoded-password>
```

4. **Create Deployment:**
```yaml
# k8s/deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ecommerce-app
  namespace: ecommerce
spec:
  replicas: 3
  selector:
    matchLabels:
      app: ecommerce-app
  template:
    metadata:
      labels:
        app: ecommerce-app
    spec:
      containers:
      - name: ecommerce-app
        image: ecommerce-app:latest
        ports:
        - containerPort: 8080
        envFrom:
        - configMapRef:
            name: ecommerce-config
        - secretRef:
            name: ecommerce-secrets
        livenessProbe:
          httpGet:
            path: /api/health/live
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 30
        readinessProbe:
          httpGet:
            path: /api/health/ready
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
```

5. **Create Service:**
```yaml
# k8s/service.yaml
apiVersion: v1
kind: Service
metadata:
  name: ecommerce-service
  namespace: ecommerce
spec:
  selector:
    app: ecommerce-app
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
  type: LoadBalancer
```

6. **Apply Kubernetes manifests:**
```bash
kubectl apply -f k8s/
```

## Database Setup

### PostgreSQL Production Setup

1. **Create database and user:**
```sql
CREATE DATABASE ecommerce;
CREATE USER ecommerce WITH ENCRYPTED PASSWORD 'your-secure-password';
GRANT ALL PRIVILEGES ON DATABASE ecommerce TO ecommerce;
```

2. **Run initialization script:**
```bash
psql -h your-db-host -U ecommerce -d ecommerce -f scripts/init-db.sql
```

### Database Migration

For production deployments, consider using Flyway or Liquibase for database migrations:

```xml
<!-- Add to pom.xml -->
<plugin>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-maven-plugin</artifactId>
    <version>9.22.3</version>
    <configuration>
        <url>jdbc:postgresql://your-db-host:5432/ecommerce</url>
        <user>ecommerce</user>
        <password>your-secure-password</password>
    </configuration>
</plugin>
```

## Monitoring and Observability

### Application Metrics

The application exposes Prometheus metrics at `/actuator/prometheus`.

#### Prometheus Configuration

```yaml
# prometheus.yml
scrape_configs:
  - job_name: 'ecommerce-app'
    static_configs:
      - targets: ['ecommerce-service:80']
    metrics_path: '/actuator/prometheus'
    scrape_interval: 15s
```

#### Grafana Dashboard

Import the Spring Boot dashboard (ID: 12900) and configure data source.

### Logging

#### Centralized Logging with ELK Stack

1. **Logstash configuration:**
```ruby
input {
  beats {
    port => 5044
  }
}

filter {
  if [fields][service] == "ecommerce-app" {
    grok {
      match => { "message" => "%{TIMESTAMP_ISO8601:timestamp} - %{GREEDYDATA:log_message}" }
    }
  }
}

output {
  elasticsearch {
    hosts => ["elasticsearch:9200"]
    index => "ecommerce-logs-%{+YYYY.MM.dd}"
  }
}
```

2. **Filebeat configuration:**
```yaml
filebeat.inputs:
- type: container
  paths:
    - '/var/lib/docker/containers/*/*.log'
  fields:
    service: ecommerce-app
  fields_under_root: true

output.logstash:
  hosts: ["logstash:5044"]
```

## Security Considerations

### Production Security Checklist

- [ ] Use HTTPS/TLS encryption
- [ ] Implement proper authentication and authorization
- [ ] Use secure database connections
- [ ] Enable security headers
- [ ] Implement rate limiting
- [ ] Use secrets management (Vault, K8s secrets)
- [ ] Regular security updates
- [ ] Network security (firewalls, VPCs)
- [ ] Container security scanning
- [ ] Backup and disaster recovery

### Environment Variables

**Required Production Environment Variables:**

```bash
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://db-host:5432/ecommerce
SPRING_DATASOURCE_USERNAME=ecommerce
SPRING_DATASOURCE_PASSWORD=secure-password

# Application
SPRING_PROFILES_ACTIVE=prod
JAVA_OPTS="-Xmx1g -Xms512m -XX:+UseG1GC"

# Security
JWT_SECRET=your-jwt-secret-key
JWT_EXPIRATION=86400

# External Services
REDIS_URL=redis://redis-host:6379
ELASTICSEARCH_URL=http://elasticsearch:9200
```

## Performance Tuning

### JVM Tuning

```bash
# Production JVM settings
JAVA_OPTS="
  -Xmx1g 
  -Xms512m 
  -XX:+UseG1GC 
  -XX:+UseStringDeduplication 
  -XX:+OptimizeStringConcat 
  -Djava.security.egd=file:/dev/./urandom
"
```

### Database Connection Pool

```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      idle-timeout: 300000
      max-lifetime: 1200000
      connection-timeout: 20000
```

## Backup and Recovery

### Database Backup

```bash
# Daily backup script
#!/bin/bash
DATE=$(date +%Y%m%d_%H%M%S)
BACKUP_DIR="/backups/ecommerce"
DB_NAME="ecommerce"
DB_USER="ecommerce"
DB_HOST="db-host"

mkdir -p $BACKUP_DIR
pg_dump -h $DB_HOST -U $DB_USER -d $DB_NAME > $BACKUP_DIR/ecommerce_$DATE.sql

# Keep only last 7 days of backups
find $BACKUP_DIR -name "ecommerce_*.sql" -mtime +7 -delete
```

### Application Data Backup

```bash
# Backup application logs and configuration
tar -czf /backups/app_config_$(date +%Y%m%d).tar.gz \
  /app/config \
  /app/logs \
  /app/data
```

## Troubleshooting

### Common Issues

1. **Application won't start:**
   - Check Java version (requires 17+)
   - Verify database connectivity
   - Check application logs

2. **Database connection issues:**
   - Verify database credentials
   - Check network connectivity
   - Ensure database is running

3. **Performance issues:**
   - Monitor JVM metrics
   - Check database query performance
   - Review application logs for errors

### Health Checks

```bash
# Application health
curl http://localhost:8080/api/health

# Database connectivity
curl http://localhost:8080/actuator/health/db

# Application metrics
curl http://localhost:8080/actuator/metrics
```

## CI/CD Pipeline

The application includes a GitHub Actions pipeline that:

1. Builds the application
2. Runs unit tests
3. Performs code analysis
4. Builds Docker image
5. Deploys to staging/production

See `.github/workflows/ci.yml` for complete pipeline configuration.

## Support

For deployment support:
- Email: devops@ecommerce.com
- Documentation: [Deployment Docs](https://docs.ecommerce.com/deployment)
- GitHub Issues: [Repository Issues](https://github.com/kunbisht/springboot-ecommerce-application-tester/issues)
