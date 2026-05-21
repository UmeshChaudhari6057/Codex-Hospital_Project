# Hospital Management Microservices - Architecture

## 🏥 Overview

This is a comprehensive Hospital Management System built using **Spring Boot Microservices Architecture** with **Service Discovery**, **API Gateway**, **Event-Driven Communication**, and **MySQL Database**.

---

## 🏗️ System Architecture

### **High-Level Architecture**

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Client Apps   │────│   API Gateway   │────│  Eureka Server  │
│  (Web/Mobile)   │    │   (Port 8080)   │    │   (Port 8761)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │                       │
                                ▼                       ▼
┌─────────────────┬─────────────────┬─────────────────┬─────────────────┐
│   User Service  │ Patient Service │ Doctor Service  │Appointment Svc │
│   (Port 8081)   │   (Port 8082)   │   (Port 8083)   │   (Port 8084)   │
│                 │                 │                 │                 │
│ • Authentication│ • Patient Mgmt │ • Doctor Mgmt   │ • Booking Svc  │
│ • User Profiles │ • Medical Rec  │ • Schedules     │ • Notifications│
└─────────────────┴─────────────────┴─────────────────┴─────────────────┘
         │                   │                   │                   │
         └───────────────────┼───────────────────┼───────────────────┘
                             │                   │
                             ▼                   ▼
                    ┌─────────────────┐   ┌─────────────────┐
                    │   MySQL DB      │   │   Kafka Broker  │
                    │                 │   │                 │
                    │ • user_db       │   │ • Events        │
                    │ • patient_db    │   │ • Notifications│
                    │ • doctor_db     │   │ • Logging      │
                    │ • appointment_db│   └─────────────────┘
                    └─────────────────┘
```

---

## 🚀 Microservices Details

### **1. Discovery Service (Eureka Server)**
- **Port**: 8761
- **Purpose**: Service Registry & Discovery
- **Technology**: Spring Cloud Eureka Server
- **Responsibilities**:
  - Register all microservices
  - Service discovery and load balancing
  - Health monitoring of services

### **2. API Gateway**
- **Port**: 8080
- **Purpose**: Single Entry Point & Request Routing
- **Technology**: Spring Cloud Gateway
- **Responsibilities**:
  - Route requests to appropriate microservices
  - Load balancing via Eureka
  - Circuit breaker patterns (Resilience4j)
  - Cross-cutting concerns (security, logging)

**Routes Configuration**:
```
/api/v1/auth/**      → User Service (Authentication)
/api/v1/users/**     → User Service (User Management)
/api/v1/patients/**  → Patient Service
/api/v1/doctors/**   → Doctor Service
/api/v1/appointments/** → Appointment Service
```

### **3. User Service**
- **Port**: 8081
- **Purpose**: User Management & Authentication
- **Technology**: Spring Boot, Spring Security, JWT
- **Database**: MySQL (user_db)
- **Responsibilities**:
  - User registration and authentication
  - JWT token generation and validation
  - User profile management
  - Role-based access control

### **4. Patient Service**
- **Port**: 8082
- **Purpose**: Patient Management
- **Technology**: Spring Boot, Spring Data JPA
- **Database**: MySQL (patient_db)
- **Responsibilities**:
  - Patient registration and profiles
  - Medical records management
  - Patient history tracking

### **5. Doctor Service**
- **Port**: 8083
- **Purpose**: Doctor Management
- **Technology**: Spring Boot, Spring Data JPA
- **Database**: MySQL (doctor_db)
- **Responsibilities**:
  - Doctor profiles and specializations
  - Schedule management
  - Availability tracking

### **6. Appointment Service**
- **Port**: 8084
- **Purpose**: Appointment Management
- **Technology**: Spring Boot, Spring Kafka, OpenFeign
- **Database**: MySQL (appointment_db)
- **Responsibilities**:
  - Appointment booking and management
  - Inter-service communication (Feign clients)
  - Event publishing (Kafka)
  - Notification handling

---

## 🔄 Communication Patterns

### **1. Synchronous Communication**
- **Technology**: OpenFeign (HTTP/REST)
- **Use Cases**: Real-time data validation
- **Example**: Appointment Service validates Patient/Doctor existence

```java
// Feign Client Example
@FeignClient(name = "patient-service")
public interface PatientClient {
    @GetMapping("/patients/{id}")
    PatientDto getPatientById(@PathVariable("id") Long id);
}
```

### **2. Asynchronous Communication**
- **Technology**: Apache Kafka
- **Use Cases**: Event-driven notifications
- **Example**: Appointment events → Notification service

```java
// Kafka Event Example
@KafkaListener(topics = "appointment-events")
public void handleAppointmentEvent(AppointmentEvent event) {
    // Process appointment events
}
```

---

## 🛡️ Resilience Patterns

### **Circuit Breaker (Resilience4j)**
- **Configuration**: 50% failure rate threshold
- **Fallback**: Graceful degradation
- **Recovery**: 5-second wait duration

```yaml
resilience4j:
  circuitbreaker:
    instances:
      user-service:
        failure-rate-threshold: 50
        wait-duration-in-open-state: 5s
        sliding-window-size: 10
```

---

## 🗄️ Database Architecture

### **Database-per-Service Pattern**
Each microservice has its own MySQL database:

| Service | Database | Purpose |
|---------|----------|---------|
| User Service | user_db | Users, roles, authentication |
| Patient Service | patient_db | Patient profiles, medical records |
| Doctor Service | doctor_db | Doctor profiles, schedules |
| Appointment Service | appointment_db | Appointments, bookings |

### **Database Configuration**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/service_db
    username: root
    password: ${MYSQL_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
```

---

## 🔐 Security Architecture

### **JWT-Based Authentication**
- **Token Generation**: User Service
- **Token Validation**: API Gateway + Services
- **Claims**: User ID, roles, expiration

```java
// JWT Token Structure
{
  "sub": "john_doe",
  "roles": ["PATIENT"],
  "exp": 1640995200,
  "iat": 1640991600
}
```

### **Security Flow**
1. Client → API Gateway (with JWT)
2. Gateway validates JWT
3. Gateway forwards request to microservice
4. Microservice validates JWT again
5. Process request and return response

---

## 📊 Technology Stack

### **Backend Technologies**
- **Framework**: Spring Boot 3.2.5
- **Cloud**: Spring Cloud 2023.0.1
- **Database**: MySQL 8.0
- **Messaging**: Apache Kafka
- **Discovery**: Eureka Server
- **Gateway**: Spring Cloud Gateway
- **Security**: Spring Security + JWT
- **Resilience**: Resilience4j
- **Communication**: OpenFeign
- **Mapping**: ModelMapper
- **Build Tool**: Maven
- **Java Version**: 21

### **Development Tools**
- **IDE**: IntelliJ Rider/IDEA
- **API Testing**: Postman/Insomnia
- **Database**: MySQL Workbench
- **Containerization**: Docker (available)

---

## 🚦 Service Port Allocation

| Service | Port | Purpose |
|---------|------|---------|
| Discovery Service | 8761 | Service Registry |
| API Gateway | 8080 | Request Routing |
| User Service | 8081 | User Management |
| Patient Service | 8082 | Patient Management |
| Doctor Service | 8083 | Doctor Management |
| Appointment Service | 8084 | Appointment Management |
| Kafka | 9092 | Message Broker |
| MySQL | 3306 | Database Server |

---

## 🔄 Data Flow Examples

### **Patient Registration Flow**
```
1. Client → POST /api/v1/patients (API Gateway)
2. Gateway → Route to Patient Service
3. Patient Service → Save to patient_db
4. Patient Service → Return patient data
5. Gateway → Return response to client
```

### **Appointment Booking Flow**
```
1. Client → POST /api/v1/appointments (API Gateway)
2. Gateway → Route to Appointment Service
3. Appointment Service → Validate patient (Feign → Patient Service)
4. Appointment Service → Validate doctor (Feign → Doctor Service)
5. Appointment Service → Save to appointment_db
6. Appointment Service → Publish event (Kafka)
7. Appointment Service → Return appointment data
8. Gateway → Return response to client
```

---

## 📈 Scalability & Performance

### **Horizontal Scalability**
- Each service can be scaled independently
- Load balancing through Eureka
- Circuit breakers prevent cascading failures

### **Performance Optimizations**
- Database connection pooling
- Caching strategies (Redis can be added)
- Asynchronous processing with Kafka
- Circuit breaker patterns

---

## 🔧 Configuration Management

### **Environment-Specific Configurations**
- **Development**: Local MySQL, Kafka
- **Production**: Containerized MySQL, Kafka clusters
- **Testing**: H2 Database, TestContainers

### **Configuration Sources**
- **application.yml**: Service-specific configuration
- **Environment Variables**: Sensitive data
- **Spring Cloud Config**: Centralized configuration (future)

---

## 🚀 Deployment Architecture

### **Current Deployment**
- **Local Development**: Individual Spring Boot applications
- **Database**: Local MySQL instance
- **Messaging**: Local Kafka instance

### **Future Deployment Options**
- **Docker Containerization**: All services in containers
- **Kubernetes**: Orchestration and scaling
- **Cloud Deployment**: AWS/Azure/GCP
- **CI/CD**: Jenkins/GitHub Actions pipeline

---

## 📋 API Documentation

### **Gateway Endpoints**
```
Authentication:
POST /api/v1/auth/register
POST /api/v1/auth/login

User Management:
GET /api/v1/users
GET /api/v1/users/{id}
POST /api/v1/users
PUT /api/v1/users/{id}
DELETE /api/v1/users/{id}

Patient Management:
GET /api/v1/patients
GET /api/v1/patients/{id}
POST /api/v1/patients
PUT /api/v1/patients/{id}
DELETE /api/v1/patients/{id}

Doctor Management:
GET /api/v1/doctors
GET /api/v1/doctors/{id}
POST /api/v1/doctors
PUT /api/v1/doctors/{id}
DELETE /api/v1/doctors/{id}

Appointment Management:
GET /api/v1/appointments
GET /api/v1/appointments/{id}
POST /api/v1/appointments
PUT /api/v1/appointments/{id}
DELETE /api/v1/appointments/{id}
```

---

## 🔍 Monitoring & Observability

### **Current Monitoring**
- **Spring Boot Actuator**: Health checks, metrics
- **Eureka Dashboard**: Service registry status
- **Application Logs**: Service-specific logs

### **Future Enhancements**
- **Distributed Tracing**: Zipkin/Sleuth
- **Metrics Collection**: Prometheus + Grafana
- **Log Aggregation**: ELK Stack
- **APM**: New Relic/DataDog

---

## 🧪 Testing Strategy

### **Unit Testing**
- JUnit 5 for service logic
- Mockito for mocking dependencies
- Test coverage for business logic

### **Integration Testing**
- @SpringBootTest for full application context
- TestContainers for database testing
- WireMock for external service mocking

### **End-to-End Testing**
- API Gateway integration tests
- Cross-service communication tests
- Performance testing with JMeter

---

## 🔄 Development Workflow

### **Local Development**
1. Start MySQL database
2. Start Kafka broker
3. Start Discovery Service (8761)
4. Start API Gateway (8080)
5. Start individual microservices
6. Test via API Gateway

### **Build & Run Commands**
```bash
# Build all services
mvn clean install

# Run individual service
cd service-name
mvn spring-boot:run

# Run all services (batch script)
./start-services.bat
```

---

## 🎯 Best Practices Implemented

### **Microservices Best Practices**
- ✅ Single Responsibility per service
- ✅ Database per service pattern
- ✅ Service discovery and registration
- ✅ API Gateway pattern
- ✅ Circuit breaker pattern
- ✅ Asynchronous communication
- ✅ Centralized configuration
- ✅ Containerization ready

### **Security Best Practices**
- ✅ JWT-based authentication
- ✅ Role-based access control
- ✅ Secure inter-service communication
- ✅ Environment variable secrets

### **Code Quality**
- ✅ Clean architecture principles
- ✅ DTO pattern for API contracts
- ✅ Proper exception handling
- ✅ Logging and monitoring
- ✅ Test coverage

---

## 🚀 Future Enhancements

### **Planned Features**
- **Frontend Application**: React/Angular web app
- **Mobile App**: React Native/Flutter
- **Real-time Notifications**: WebSocket integration
- **File Storage**: Medical documents, images
- **Payment Integration**: Billing system
- **Reporting Module**: Analytics and reports
- **Video Consultation**: Telemedicine features

### **Technical Improvements**
- **Docker Compose**: Complete containerized setup
- **Kubernetes**: Production deployment
- **CI/CD Pipeline**: Automated testing and deployment
- **API Documentation**: Swagger/OpenAPI integration
- **Distributed Caching**: Redis integration
- **Message Queues**: RabbitMQ alternative

---

## 📞 Support & Contact

### **Project Information**
- **Architecture**: Microservices with Spring Cloud
- **Database**: MySQL with service-per-database pattern
- **Communication**: REST + Kafka for events
- **Security**: JWT-based authentication
- **Monitoring**: Spring Boot Actuator + Eureka

### **Getting Help**
- Check service logs for errors
- Verify Eureka dashboard for service status
- Test individual service endpoints directly
- Check database connectivity
- Verify Kafka broker status

---

**This architecture provides a scalable, maintainable, and robust foundation for a modern hospital management system.** 🏥✨
