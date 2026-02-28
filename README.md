# Desafio2-DSE

Basic User CRUD following DDD/Hexagonal structure (domain, application, infrastructure).

## Endpoints
- `POST /api/users`
- `PUT /api/users/{id}`
- `GET /api/users/{id}`
- `GET /api/users`
- `DELETE /api/users/{id}`

## Validation
`nombre`: required, 2-100 chars.
`email`: required, valid email, max 150 chars.

## Run Tests
```
./gradlew.bat test
```

