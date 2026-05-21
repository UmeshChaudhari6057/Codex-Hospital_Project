@echo off
echo Building and Starting Hospital Management Microservices...
echo.

REM Set Maven environment
set MAVEN_HOME=C:\Maven\apache-maven-3.9.15
set PATH=%MAVEN_HOME%\bin;%PATH%

REM Build all services first
echo Building all services...
call mvn clean install

REM Start all services with delays
echo.
echo Starting all services...

REM Start Discovery Service (must be first)
echo Starting Discovery Service...
start "Discovery Service" cmd /k "cd /d %~dp0discovery-service && mvn spring-boot:run"

REM Wait for Discovery Service
echo Waiting for Discovery Service to start...
timeout /t 45 /nobreak >nul

REM Start other services in parallel
echo Starting remaining services...
start "API Gateway" cmd /k "cd /d %~dp0api-gateway && mvn spring-boot:run"
start "User Service" cmd /k "cd /d %~dp0user-service && mvn spring-boot:run"
start "Patient Service" cmd /k "cd /d %~dp0patient-service && mvn spring-boot:run"
start "Doctor Service" cmd /k "cd /d %~dp0doctor-service && mvn spring-boot:run"
start "Appointment Service" cmd /k "cd /d %~dp0appointment-service && mvn spring-boot:run"

echo.
echo All services starting...
echo Check Eureka Dashboard: http://localhost:8761
echo Check API Gateway: http://localhost:8080
echo.
pause
