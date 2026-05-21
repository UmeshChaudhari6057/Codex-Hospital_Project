# Hospital Management Microservices Startup Script
Write-Host "Starting Hospital Management Microservices..." -ForegroundColor Green

# Set Maven environment
$env:MAVEN_HOME="C:\Maven\apache-maven-3.9.15"
$env:PATH="$env:MAVEN_HOME\bin;$env:PATH"

# Function to start service in new window
function Start-Service($serviceName, $port, $path) {
    Write-Host "Starting $serviceName on port $port..." -ForegroundColor Yellow
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$path'; mvn spring-boot:run" -WindowStyle Normal
}

# Start Discovery Service (must be first)
Start-Service "Discovery Service" "8761" "$PSScriptRoot\discovery-service"
Start-Sleep -Seconds 30

# Start API Gateway
Start-Service "API Gateway" "8080" "$PSScriptRoot\api-gateway"
Start-Sleep -Seconds 15

# Start User Service
Start-Service "User Service" "8081" "$PSScriptRoot\user-service"
Start-Sleep -Seconds 10

# Start Patient Service
Start-Service "Patient Service" "8082" "$PSScriptRoot\patient-service"
Start-Sleep -Seconds 10

# Start Doctor Service
Start-Service "Doctor Service" "8083" "$PSScriptRoot\doctor-service"
Start-Sleep -Seconds 10

# Start Appointment Service
Start-Service "Appointment Service" "8084" "$PSScriptRoot\appointment-service"

Write-Host "`nAll services started!" -ForegroundColor Green
Write-Host "Eureka Dashboard: http://localhost:8761" -ForegroundColor Cyan
Write-Host "API Gateway: http://localhost:8080" -ForegroundColor Cyan
Write-Host "`nPress Enter to exit..."
Read-Host
