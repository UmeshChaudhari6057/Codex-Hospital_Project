# 🔧 Hospital Management Microservices - Troubleshooting Guide

## 🚨 Common Issues & Quick Fixes

### **❌ Port Already in Use**

**Problem**: `Web server failed to start. Port XXXX was already in use`

**Quick Fix**:
```bash
# Kill all Java processes
taskkill /IM java.exe /F

# Then restart services
.\start-all.bat
```

**Alternative Fix**:
```bash
# Find specific process using port
netstat -ano | findstr ":8761"
taskkill /PID <PID> /F
```

---

### **❌ Maven Not Found**

**Problem**: `'mvn' is not recognized as an internal or external command`

**Quick Fix**:
```bash
# Set Maven environment temporarily
set MAVEN_HOME=C:\Maven\apache-maven-3.9.15
set PATH=%MAVEN_HOME%\bin;%PATH%

# Verify
mvn -version
```

**Permanent Fix**:
1. Right-click "This PC" → Properties
2. Advanced system settings → Environment Variables
3. Add `MAVEN_HOME=C:\Maven\apache-maven-3.9.15`
4. Add `%MAVEN_HOME%\bin` to PATH
5. Restart terminal

---

### **❌ Database Connection Failed**

**Problem**: `Could not create connection to database server`

**Quick Fix**:
```bash
# Check MySQL is running
mysql -u root -p

# Create databases if missing
mysql -u root -p -e "
CREATE DATABASE IF NOT EXISTS user_db;
CREATE DATABASE IF NOT EXISTS patient_db;
CREATE DATABASE IF NOT EXISTS doctor_db;
CREATE DATABASE IF NOT EXISTS appointment_db;
"
```

**Check Database Configuration**:
```yaml
# In service-name/src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/service_db
    username: root
    password: ${MYSQL_PASSWORD}
```

---

### **❌ Service Not Registering in Eureka**

**Problem**: Service starts but doesn't appear in Eureka Dashboard

**Quick Fix**:
1. **Check Eureka is running**: http://localhost:8761
2. **Verify service configuration**:
```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
    register-with-eureka: true
    fetch-registry: true
```
3. **Restart the service** after Eureka is fully running

---

### **❌ API Gateway 404 Errors**

**Problem**: `{"status": 404, "error": "Not Found"}` for API endpoints

**Quick Fix**:
1. **Check route configuration** in `api-gateway/src/main/resources/application.yml`
2. **Verify target service is running**
3. **Check service registration in Eureka**
4. **Test service directly** (bypass gateway):
```bash
# Test user service directly
curl http://localhost:8081/api/v1/users
```

---

### **❌ ModelMapper Bean Not Found**

**Problem**: `Parameter X of constructor required a bean of type 'org.modelmapper.ModelMapper'`

**Quick Fix**:
1. **Check ModelMapper dependency** in `pom.xml`:
```xml
<dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
</dependency>
```
2. **Verify ModelMapperConfig.java exists**:
```java
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
```

---

### **❌ Build Failures**

**Problem**: `BUILD FAILURE` during Maven compilation

**Quick Fix**:
```bash
# Clean and rebuild
mvn clean install -DskipTests

# If still failing, check specific errors
mvn clean compile
```

**Common Build Issues**:
- **Java version mismatch**: Ensure Java 21+
- **Dependency conflicts**: Check `pom.xml` versions
- **Syntax errors**: Check Java files for compilation errors

---

### **❌ Kafka Connection Issues**

**Problem**: `Failed to construct consumer for kafka`

**Quick Fix**:
1. **Check Kafka is running**: Default port 9092
2. **Verify Kafka configuration**:
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
```
3. **Start Kafka** (if using local installation)

---

## 🔍 Diagnostic Commands

### **Check All Services Status**
```bash
netstat -ano | findstr ":8761 :8080 :8081 :8082 :8083 :8084"
```

### **Check Java Processes**
```bash
jps -l
```

### **Check Maven Version**
```bash
mvn -version
```

### **Check Java Version**
```bash
java -version
```

### **Check MySQL Connection**
```bash
mysql -u root -p -e "SHOW DATABASES;"
```

### **Check Service Logs**
```bash
cd service-name
mvn spring-boot:run
# Watch console output for errors
```

---

## 🛠️ Step-by-Step Troubleshooting

### **When Nothing Works**

1. **Kill Everything**
```bash
taskkill /IM java.exe /F
```

2. **Verify Prerequisites**
```bash
java -version
mvn -version
mysql -u root -p -e "SELECT 1;"
```

3. **Clean Build**
```bash
mvn clean install -DskipTests
```

4. **Start Fresh**
```bash
.\start-all.bat
```

### **When Specific Service Fails**

1. **Check Other Services**: Are other services working?
2. **Check Port Conflicts**: `netstat -ano | findstr ":PORT"`
3. **Check Configuration**: `application.yml` settings
4. **Check Dependencies**: `pom.xml` dependencies
5. **Run with Debug**: `mvn spring-boot:run -Ddebug`

---

## 📋 Service-Specific Issues

### **Discovery Service (8761)**
- **Must start first** - other services depend on it
- **Check**: http://localhost:8761
- **Common Issue**: Port 8761 already in use

### **API Gateway (8080)**
- **Depends on Discovery Service**
- **Check**: http://localhost:8080/actuator/health
- **Common Issue**: Missing route configuration

### **User Service (8081)**
- **Handles authentication**
- **Check**: POST http://localhost:8080/api/v1/auth/register
- **Common Issue**: JWT configuration problems

### **Patient Service (8082)**
- **Patient data management**
- **Check**: GET http://localhost:8080/api/v1/patients
- **Common Issue**: Database connection

### **Doctor Service (8083)**
- **Doctor data management**
- **Check**: GET http://localhost:8080/api/v1/doctors
- **Common Issue**: ModelMapper configuration

### **Appointment Service (8084)**
- **Appointment booking**
- **Check**: GET http://localhost:8080/api/v1/appointments
- **Common Issue**: Kafka connection, Feign client failures

---

## 🚨 Emergency Recovery

### **Complete System Reset**

1. **Stop Everything**
```bash
taskkill /IM java.exe /F
taskkill /IM mysqld.exe /F
```

2. **Restart MySQL**
```bash
# Start MySQL service
net start mysql
# Or restart MySQL Workbench
```

3. **Recreate Databases**
```bash
mysql -u root -p -e "
DROP DATABASE IF EXISTS user_db;
DROP DATABASE IF EXISTS patient_db;
DROP DATABASE IF EXISTS doctor_db;
DROP DATABASE IF EXISTS appointment_db;
CREATE DATABASE user_db;
CREATE DATABASE patient_db;
CREATE DATABASE doctor_db;
CREATE DATABASE appointment_db;
"
```

4. **Rebuild Everything**
```bash
mvn clean install -DskipTests
```

5. **Start Services**
```bash
.\start-all.bat
```

---

## 📞 Getting Help

### **What to Include When Asking for Help**

1. **Error Messages**: Full stack traces
2. **Service Status**: Which services are running
3. **Recent Changes**: What was modified
4. **Environment Details**: OS, Java, Maven versions
5. **Steps Taken**: What you've already tried

### **Useful Commands for Debugging**

```bash
# Show all Java processes with details
jps -l -v

# Check specific port usage
netstat -ano | findstr ":PORT"

# Test database connection
mysql -u root -p -e "SELECT NOW();"

# Test Maven build
mvn clean compile

# Run service with debug logging
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Ddebug=true"
```

---

## 🎯 Prevention Tips

### **Best Practices**

1. **Start services in order**: Discovery → Gateway → Others
2. **Check Eureka Dashboard** after each service starts
3. **Use the startup scripts** provided
4. **Keep MySQL running** in background
5. **Regularly check logs** for warnings

### **Environment Setup**

1. **Set MAVEN_HOME permanently**
2. **Add MySQL to PATH** if needed
3. **Use IDE integration** for better debugging
4. **Keep backup of working configuration**

### **Development Workflow**

1. **Test individual services** before integration
2. **Use proper shutdown**: Ctrl+C in each service window
3. **Commit working code** regularly
4. **Document any configuration changes**

---

## 📚 Additional Resources

- **Architecture Guide**: `ARCHITECTURE.md`
- **Quick Start**: `QUICK_START.md`
- **API Documentation**: Check individual service endpoints
- **Configuration Examples**: `application.yml` files

---

**💡 Remember: 90% of issues are solved by killing Java processes and restarting services!**
