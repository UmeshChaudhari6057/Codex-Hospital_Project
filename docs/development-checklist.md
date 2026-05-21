# Development Environment Checklist

Use this checklist before starting daily work on the Hospital Management System.

## Required Tools

- Java 21 or higher
- Maven 3.8 or higher
- MySQL 8.0 or higher
- Docker Desktop
- Git
- Postman or Insomnia
- VS Code or IntelliJ IDEA

## Recommended VS Code Extensions

- Extension Pack for Java
- Spring Boot Extension Pack
- Maven for Java
- GitLens
- Docker
- Prettier - Code formatter
- ESLint
- Angular Language Service

## Local Databases

Create these MySQL databases:

```sql
CREATE DATABASE user_db;
CREATE DATABASE patient_db;
CREATE DATABASE doctor_db;
CREATE DATABASE appointment_db;
```

## Environment Variables

Create a local `.env` file from `.env.example` and update the values:

```env
MYSQL_USERNAME=root
MYSQL_PASSWORD=your-password
JWT_SECRET=your-long-secure-secret
```

Never commit real secrets.

## Backend Startup Order

Start services in this order:

1. discovery-service
2. api-gateway
3. user-service
4. patient-service
5. doctor-service
6. appointment-service

## Verification URLs

- Eureka Dashboard: `http://localhost:8761`
- API Gateway: `http://localhost:8080`
- Gateway Health: `http://localhost:8080/actuator/health`

## Daily Start Checklist

- Pull latest `main`.
- Create or switch to the correct `feature/day-N` branch.
- Confirm MySQL is running.
- Confirm Kafka is running if testing appointment events.
- Run `mvn clean install -DskipTests`.
- Start services in order.
- Test gateway health endpoint.

## Daily End Checklist

- Run build/test commands.
- Update documentation if behavior changed.
- Commit with a clear message.
- Push branch to GitHub.
- Create or update Pull Request.
