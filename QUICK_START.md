# 🚀 Hospital Management Microservices - Quick Start Guide

## ⚡ Quick Start (5 Minutes)

### **🎯 One-Command Startup**
```bash
cd C:\Users\admin\OneDrive\Documents\Java_Project\hospital-management-microservices\hospital-management-microservices
.\start-all.bat
```
*This will build and start all 6 microservices automatically*

---

## 📋 Prerequisites Checklist

### **✅ Must Have**
- [ ] **Java 21+** installed
- [ ] **Maven 3.9+** installed
- [ ] **MySQL 8.0+** running
- [ ] **Node.js 20+ and npm** installed (for Angular frontend)
- [ ] **Git** (for version control)

### **✅ Optional but Recommended**
- [ ] **Docker** (for containerized setup)
- [ ] **Postman/Insomnia** (for API testing)
- [ ] **IDE** (IntelliJ IDEA/Eclipse)

---

## 🔧 Environment Setup

### **1. Java & Maven Setup**
```bash
# Check Java version
java -version

# Check Maven version  
mvn -version

# Set Maven environment (if needed)
set MAVEN_HOME=C:\Maven\apache-maven-3.9.15
set PATH=%MAVEN_HOME%\bin;%PATH%
```

### **2. MySQL Setup**
```sql
-- Create databases (run in MySQL Workbench)
CREATE DATABASE user_db;
CREATE DATABASE patient_db;
CREATE DATABASE doctor_db;
CREATE DATABASE appointment_db;
```

### **3. Project Setup**
```bash
# Clone or navigate to project
cd C:\Users\admin\OneDrive\Documents\Java_Project\hospital-management-microservices\hospital-management-microservices

# Build all services
mvn clean install
```

### **4. Frontend Setup**
```bash
# Go to Angular frontend
cd hospital-frontend

# Install frontend dependencies (first time only)
npm install
```

---

## 🚀 Starting Services

### **Method 1: Automated (Recommended)**
```bash
# Build and start all services
.\start-all.bat

# Or start without building (faster)
.\start-services.bat
```

### **Method 2: Manual Step-by-Step**
```bash
# 1. Start Discovery Service (MUST BE FIRST)
cd discovery-service
mvn spring-boot:run

# 2. Start API Gateway (wait 30 seconds)
cd ../api-gateway  
mvn spring-boot:run

# 3. Start remaining services (any order)
cd ../user-service
mvn spring-boot:run

cd ../patient-service
mvn spring-boot:run

cd ../doctor-service
mvn spring-boot:run

cd ../appointment-service
mvn spring-boot:run
```

### **Method 3: Start Frontend**
Open a new terminal after the backend services are running:
```bash
cd hospital-frontend
npm start
```

Angular normally starts at:
```text
http://localhost:4200
```

The frontend calls the backend API Gateway at:
```text
http://localhost:8080/api/v1
```

---

## 🌐 Service URLs

### **Main Access Points**
- **Frontend App**: http://localhost:4200
- **🏥 Eureka Dashboard**: http://localhost:8761
- **🚪 API Gateway**: http://localhost:8080
- **❤️ Health Check**: http://localhost:8080/actuator/health

### **Individual Services**
- **👤 User Service**: http://localhost:8081
- **🏥 Patient Service**: http://localhost:8082
- **👨‍⚕️ Doctor Service**: http://localhost:8083
- **📅 Appointment Service**: http://localhost:8084

---

## 🧪 Quick Testing

### **1. Check All Services Running**
```bash
netstat -ano | findstr ":4200 :8761 :8080 :8081 :8082 :8083 :8084"
```

### **2. Browser Checks**
Open these URLs in your browser:

- Frontend App: http://localhost:4200
- Eureka Dashboard: http://localhost:8761
- API Gateway Health: http://localhost:8080/actuator/health

### **3. Test User Registration**
```bash
POST http://localhost:8080/api/v1/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123", 
  "email": "john@example.com",
  "role": "PATIENT"
}
```

### **4. Test User Login**
```bash
POST http://localhost:8080/api/v1/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123"
}
```

---

## 🔍 Troubleshooting Quick Fixes

### **❌ Port Already in Use**
```bash
# Kill all Java processes
taskkill /IM java.exe /F

# Then restart services
.\start-all.bat
```

### **❌ Maven Not Found**
```bash
# Set Maven environment temporarily
set MAVEN_HOME=C:\Maven\apache-maven-3.9.15
set PATH=%MAVEN_HOME%\bin;%PATH%

# Or add to system environment variables permanently
```

### **❌ Database Connection Failed**
```bash
# Check MySQL is running
mysql -u root -p

# Verify databases exist
SHOW DATABASES;
```

### **❌ Service Not Starting**
```bash
# Check service logs
cd service-name
mvn spring-boot:run

# Look for error messages in console output
```

---

## 📝 Common Startup Commands

### **Build Commands**
```bash
# Build all services
mvn clean install

# Build specific service
cd service-name
mvn clean install

# Skip tests (faster)
mvn clean install -DskipTests
```

### **Run Commands**
```bash
# Run specific service
cd service-name
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### **Check Commands**
```bash
# Check ports
netstat -ano | findstr ":4200 :8761 :8080 :8081 :8082 :8083 :8084"

# Check Java processes
jps -l

# Check Node/npm
node -v
npm -v

# Check Maven version
mvn -version
```

---

## 🔄 Daily Workflow

### **Morning Startup**
1. **Open MySQL** (ensure it's running)
2. **Open terminal/command prompt**
3. **Navigate to project folder**
4. **Run**: `.\start-all.bat`
5. **Wait 2-3 minutes** for all services to start
6. **Open a second terminal and start frontend**:
   ```bash
   cd hospital-frontend
   npm start
   ```
7. **Open browser**: http://localhost:4200
8. **Check backend registration**: http://localhost:8761

### **Development Workflow**
```bash
# Make code changes
# Build specific service
cd service-name
mvn clean install

# Restart only that service
mvn spring-boot:run
```

### **End of Day**
```bash
# Stop all services (optional)
taskkill /IM java.exe /F

# Stop Angular frontend
# Press Ctrl + C in the frontend terminal
```

---

## 📁 Important Files

### **Configuration Files**
- `discovery-service/src/main/resources/application.yml`
- `api-gateway/src/main/resources/application.yml`
- `service-name/src/main/resources/application.yml`
- `hospital-frontend/src/app/hospital-api.ts`

### **Startup Scripts**
- `start-all.bat` - Build and start all services
- `start-services.bat` - Start all services (no build)
- `start-services.ps1` - PowerShell version

### **Database Scripts**
- `init-scripts/01-create-databases.sql`

---

## 🆘 Getting Help

### **First Things to Check**
1. **Frontend App** - http://localhost:4200
2. **Eureka Dashboard** - http://localhost:8761
3. **API Gateway Health** - http://localhost:8080/actuator/health
4. **Service logs** - Check console output
5. **Port conflicts** - `netstat` command
6. **Database connection** - MySQL status

### **Common Issues & Solutions**
- **Port conflicts**: Kill Java processes and restart
- **Maven issues**: Check MAVEN_HOME environment variable
- **Database issues**: Verify MySQL is running and databases exist
- **Build failures**: Run `mvn clean install` first

### **Debug Mode**
```bash
# Run service with debug logging
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Ddebug=true"
```

---

## 📚 Additional Resources

### **Documentation**
- `README.md` - Project overview
- `ARCHITECTURE.md` - Detailed architecture
- `DOCKER_SETUP.md` - Docker setup guide

### **API Documentation**
- Use Postman collection (if available)
- Check Swagger/OpenAPI (if configured)

### **Support**
- Check service logs for detailed error messages
- Verify all prerequisites are installed
- Ensure proper order of service startup

---

## 🎯 Success Checklist

### **✅ All Services Running?**
- [ ] Frontend App (4200)
- [ ] Discovery Service (8761)
- [ ] API Gateway (8080) 
- [ ] User Service (8081)
- [ ] Patient Service (8082)
- [ ] Doctor Service (8083)
- [ ] Appointment Service (8084)

### **✅ Services Registered in Eureka?**
- [ ] Check http://localhost:8761
- [ ] All 6 services show "UP" status

### **✅ API Gateway Working?**
- [ ] http://localhost:8080 responds
- [ ] Authentication endpoints work
- [ ] Other endpoints route correctly

### **Frontend Working?**
- [ ] http://localhost:4200 opens in browser
- [ ] Frontend can submit forms without network errors
- [ ] Browser DevTools Network tab shows API calls going to http://localhost:8080/api/v1

### **✅ Database Connected?**
- [ ] All services connect to MySQL
- [ ] No database connection errors in logs

---

**🎉 If all checkboxes are checked, your Hospital Management System is ready to use!**

---

## 🚀 Pro Tips

### **Faster Startup**
- Use `start-services.bat` (no build) if code hasn't changed
- Keep MySQL running in background
- Use IDE integration for faster development

### **Development Tips**
- Only restart the service you're working on
- Use hot reload if available
- Keep the Angular frontend running with `npm start` while editing UI code
- Check logs frequently for issues

### **Production Tips**
- Use Docker Compose for production
- Configure proper logging
- Set up monitoring and alerts

---

**💡 Remember: Discovery Service MUST start first, then API Gateway, then other services!**
