---
description: Start all Hospital Management Microservices
---

# Start All Hospital Management Microservices

This workflow will automatically start all 6 microservices in the correct order with proper delays to ensure successful startup.

## Prerequisites Check

First, verify all prerequisites are met:

### 1. Check Java Installation
```bash
java -version
```

### 2. Check Maven Installation  
```bash
mvn -version
```

### 3. Check MySQL Connection
```bash
mysql -u root -pUmesh@12345 -e "SHOW DATABASES;"
```

### 4. Check for Port Conflicts
```bash
netstat -ano | findstr ":8761 :8080 :8081 :8082 :8083 :8084"
```

## Step 1: Clean Environment

Kill any existing Java processes to avoid port conflicts:

```bash
// turbo
taskkill /IM java.exe /F
```

## Step 2: Set Maven Environment

Ensure Maven is properly configured:

```bash
// turbo
set MAVEN_HOME=C:\Maven\apache-maven-3.9.15
set PATH=%MAVEN_HOME%\bin;%PATH%
```

## Step 3: Build Project

Build all services to ensure latest code is compiled:

```bash
// turbo
mvn clean install -DskipTests
```

## Step 4: Start Discovery Service

Start the Eureka Discovery Server first (MUST BE FIRST):

```bash
cd discovery-service
mvn spring-boot:run
```

Wait for Discovery Service to fully start (look for "Started DiscoveryServiceApplication" message).

## Step 5: Start API Gateway

Start the API Gateway (must wait for Discovery Service):

```bash
cd ../api-gateway
mvn spring-boot:run
```

Wait for API Gateway to register with Eureka (look for "Started ApiGatewayApplication").

## Step 6: Start User Service

```bash
cd ../user-service
mvn spring-boot:run
```

## Step 7: Start Patient Service

```bash
cd ../patient-service
mvn spring-boot:run
```

## Step 8: Start Doctor Service

```bash
cd ../doctor-service
mvn spring-boot:run
```

## Step 9: Start Appointment Service

```bash
cd ../appointment-service
mvn spring-boot:run
```

## Step 10: Verify All Services

Check that all services are running:

```bash
netstat -ano | findstr ":8761 :8080 :8081 :8082 :8083 :8084"
```

## Step 11: Test Services

Verify services are working by checking:

1. **Eureka Dashboard**: http://localhost:8761
2. **API Gateway Health**: http://localhost:8080/actuator/health
3. **Test Authentication**: POST http://localhost:8080/api/v1/auth/register

## Troubleshooting

If any service fails to start:

1. Check the service logs for error messages
2. Verify MySQL is running and databases exist
3. Check for port conflicts
4. Kill all Java processes and restart
5. Run individual service with debug mode:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Ddebug=true"
   ```

## Success Indicators

✅ **Discovery Service** running on port 8761  
✅ **API Gateway** running on port 8080  
✅ **User Service** running on port 8081  
✅ **Patient Service** running on port 8082  
✅ **Doctor Service** running on port 8083  
✅ **Appointment Service** running on port 8084  
✅ **All services registered in Eureka**  
✅ **API Gateway routing working**  

## Quick Alternative

For faster startup (if code hasn't changed):

```bash
.\start-services.bat
```

This script starts all services without rebuilding the project.
