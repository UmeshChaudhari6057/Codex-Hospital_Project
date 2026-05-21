# Coding Standards

These standards keep the Hospital Management System clean, readable, and interview-ready.

## General Rules

- Use meaningful names for classes, methods, variables, and files.
- Keep methods small and focused.
- Avoid hardcoded values. Prefer configuration files or constants.
- Do not commit secrets, passwords, tokens, or local `.env` files.
- Add comments only when logic is not obvious.
- Update documentation when behavior changes.

## Java / Spring Boot Standards

### Naming

- Classes: `PascalCase`
- Methods: `camelCase`
- Variables: `camelCase`
- Constants: `UPPER_CASE`
- Packages: lowercase, for example `com.hospital.userservice`

### Layering

Use a clean controller-service-repository structure:

```text
controller -> service -> repository -> database
```

Recommended package structure:

```text
controller/
service/
service/impl/
repository/
entity/
dto/
mapper/
exception/
config/
security/
```

### Controller Rules

- Controllers should not contain business logic.
- Validate request DTOs.
- Return DTOs, not JPA entities.
- Use proper HTTP status codes.

### Service Rules

- Business logic belongs in service classes.
- Keep transaction boundaries in service layer.
- Handle service-specific validation here.

### Repository Rules

- Keep repository interfaces simple.
- Use derived queries where possible.
- Use custom queries only when needed.

## API Standards

Use versioned REST endpoints:

```text
/api/v1/users
/api/v1/patients
/api/v1/doctors
/api/v1/appointments
```

Use clear HTTP methods:

```text
GET    -> read
POST   -> create
PUT    -> full update
PATCH  -> partial update
DELETE -> delete
```

## Git Standards

### Branch Naming

```text
feature/day-1
feature/day-3-angular-foundation
fix/gateway-routing
chore/update-docs
```

### Commit Messages

```text
Day 1: add development checklist
Day 3: initialize frontend structure
Day 5: add authentication page plan
```

## Frontend Standards

When Angular frontend is added:

- Use feature-based folders.
- Use Angular Reactive Forms.
- Use Angular Material components.
- Keep API calls inside service classes.
- Use guards for protected routes.
- Use interceptors for JWT tokens.

Recommended structure:

```text
src/app/core
src/app/shared
src/app/features/auth
src/app/features/dashboard
src/app/features/patients
src/app/features/doctors
src/app/features/appointments
```
