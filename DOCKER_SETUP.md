# Docker Setup Guide

This guide explains how to set up and run the Hospital Management Microservices using Docker and Docker Compose.

## Prerequisites

- Docker Desktop installed and running
- Docker Compose (included with Docker Desktop)
- At least 4GB RAM available for Docker

## Quick Start

### 1. Build the Project

First, build all microservices using Maven:

```bash
cd hospital-management-microservices
mvn clean install
```

### 2. Start All Services

Run the complete stack with Docker Compose:

```bash
docker-compose up -d
```

This will start:
- MySQL database (port 3306)
- Zookeeper (port 2181)
- Kafka (port 9092)
- Discovery Service (port 8761)
- API Gateway (port 8080)
- User Service (port 8081)
- Patient Service (port 8082)
- Doctor Service (port 8083)
- Appointment Service (port 8084)

### 3. Verify Services

Check that all services are running:

```bash
docker-compose ps
```

Test the services:
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Health Check**: http://localhost:8080/actuator/health

## Service Management

### View Logs

```bash
# View all logs
docker-compose logs

# View specific service logs
docker-compose logs discovery-service
docker-compose logs api-gateway
docker-compose logs user-service
```

### Stop Services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (will delete database data)
docker-compose down -v
```

### Restart Services

```bash
# Restart all services
docker-compose restart

# Restart specific service
docker-compose restart user-service
```

## Database Access

The MySQL database is accessible at:
- **Host**: localhost
- **Port**: 3306
- **Username**: root
- **Password**: root

You can connect using any MySQL client:
```bash
mysql -h localhost -P 3306 -u root -p
```

## Troubleshooting

### Port Conflicts

If you have services running on the required ports, you can either:
1. Stop the conflicting services
2. Modify the port mappings in docker-compose.yml

### Service Startup Issues

If services fail to start:
1. Check the logs: `docker-compose logs [service-name]`
2. Ensure all JAR files are built: `mvn clean install`
3. Verify Docker has enough memory allocated

### Database Connection Issues

If services can't connect to the database:
1. Ensure MySQL container is running: `docker-compose ps mysql`
2. Check database initialization: `docker-compose logs mysql`
3. Verify database creation: Connect to MySQL and check databases

## Development Workflow

### Rebuild Services After Code Changes

1. Build the updated service:
```bash
cd [service-directory]
mvn clean package
```

2. Restart the service:
```bash
docker-compose up -d [service-name]
```

### Add New Services

1. Create Dockerfile in the new service directory
2. Add service definition to docker-compose.yml
3. Update the init-scripts if new database is needed
4. Run `docker-compose up -d [new-service]`

## Environment Variables

You can customize the configuration by modifying environment variables in docker-compose.yml:

- Database credentials
- Kafka configuration
- Service ports
- Eureka URLs

## Production Considerations

For production deployment:
1. Use environment-specific configuration files
2. Implement proper secrets management
3. Add health checks and monitoring
4. Configure proper resource limits
5. Set up backup strategies for databases
