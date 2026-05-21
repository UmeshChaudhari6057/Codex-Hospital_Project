@echo off
echo Starting Hospital Management Microservices...
echo.

REM Set Maven environment
set MAVEN_HOME=C:\Maven\apache-maven-3.9.15
set PATH=%MAVEN_HOME%\bin;%PATH%

REM Start Discovery Service (must be first)
echo Starting Discovery Service on port 8761...
start "Discovery Service" cmd /k "cd /d %~dp0discovery-service && mvn spring-boot:run"

REM Wait 30 seconds for Discovery Service to start
echo Waiting for Discovery Service to start...
timeout /t 30 /nobreak >nul

REM Start API Gateway
echo Starting API Gateway on port 8080...
start "API Gateway" cmd /k "cd /d %~dp0api-gateway && mvn spring-boot:run"

REM Wait 15 seconds
timeout /t 15 /nobreak >nul

REM Start User Service
echo Starting User Service on port 8081...
start "User Service" cmd /k "cd /d %~dp0user-service && mvn spring-boot:run"

REM Wait 10 seconds
timeout /t 10 /nobreak >nul

REM Start Patient Service
echo Starting Patient Service on port 8082...
start "Patient Service" cmd /k "cd /d %~dp0patient-service && mvn spring-boot:run"

REM Wait 10 seconds
timeout /t 10 /nobreak >nul

REM Start Doctor Service
echo Starting Doctor Service on port 8083...
start "Doctor Service" cmd /k "cd /d %~dp0doctor-service && mvn spring-boot:run"

REM Wait 10 seconds
timeout /t 10 /nobreak >nul

REM Start Appointment Service
echo Starting Appointment Service on port 8084...
start "Appointment Service" cmd /k "cd /d %~dp0appointment-service && mvn spring-boot:run"

echo.
echo All services starting...
echo Check Eureka Dashboard: http://localhost:8761
echo Check API Gateway: http://localhost:8080
echo.
pause
