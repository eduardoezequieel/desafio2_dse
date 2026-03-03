# Desafio2-DSE

Integrantes de Grupo: 
1. Diego Guillermo Esnard Romero ER231474
2. Eduardo Ezequiel López Rivera LR230061
3. Diego René López Martínez LM231893 
4. Vladimir Alexander Ayala Cabrera AC202262
5. Daniel José Menjivar Escobar ME180718

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

