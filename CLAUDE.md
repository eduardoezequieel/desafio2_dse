# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
./gradlew build          # Full build
./gradlew compileJava    # Compile only
./gradlew bootRun        # Run the application
./gradlew test           # Run all tests
./gradlew test --tests "com.udb.desafio2.dse.domain.user.model.UserTest"  # Run a single test class
```

On Windows use `./gradlew.bat` instead of `./gradlew`.

The app runs on the default Spring Boot port (8080). H2 console is available at `/h2-console`.

## Architecture

DDD/Hexagonal (Ports & Adapters) with three layers:

- **`domain/`** — Pure Java entities (`User`, `Role` enum) and repository interfaces (ports). No Spring dependencies. Business validation lives here.
- **`application/`** — Service orchestration (`UserService`, `AuthService`) and DTOs with Jakarta validation annotations. Services coordinate domain logic with infrastructure.
- **`infrastructure/`** — Spring framework integration:
  - `persistence/` — JPA entities (`UserEntity`), Spring Data repositories, and adapter implementations that bridge domain repository interfaces
  - `security/` — JWT token provider, authentication filter (reads JWT from HttpOnly cookie), custom entry point and access denied handler
  - `web/` — REST controllers (`UserController`, `AuthController`), Thymeleaf view controller (`ViewController`), global exception handler
  - `config/` — Spring Security config, H2 console config

## Key Patterns

- **Repository adapter pattern**: Domain defines `UserRepository` interface; `UserRepositoryImpl` in infrastructure adapts it to `UserJpaRepository` (Spring Data). Domain `User` and JPA `UserEntity` are separate classes.
- **JWT via HttpOnly cookie**: Auth endpoints set a `jwt` cookie on login/register. `JwtAuthenticationFilter` reads from the cookie (not Authorization header). Logout clears the cookie.
- **Constructor injection everywhere** via Lombok `@RequiredArgsConstructor`.
- **Security**: `/dashboard/**` requires authentication (server-side redirect to `/login`). `/api/users/**` requires `ROLE_ADMIN`. Auth endpoints and static resources are public.
- **Frontend**: Thymeleaf templates with vanilla JS. `localStorage` stores display-only user info (name, email, role) — not the JWT token.

## Tech Stack

- Java 17, Spring Boot 4.0.2, Spring Security, Spring Data JPA
- H2 database (file-based at `./data/desafio2`, in-memory for tests)
- JJWT 0.12.3 for JWT handling
- Lombok, Thymeleaf, Jakarta Validation
- JUnit 5 for testing

## Code Style

- Mixed Spanish/English naming: UI text and some variable names in Spanish, class/method names in English
- DTOs use Jakarta validation annotations (`@NotBlank`, `@Email`, `@Size`)
- Prefer constructor injection (`@RequiredArgsConstructor`) over `@Autowired`
