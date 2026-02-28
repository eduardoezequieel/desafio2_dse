# Project Guidelines for Desafio2-DSE

## Instructions
- Do not create unnecessary markdown files
- Do not execute checks that are not relevant to the project
- Focus on writing clean, maintainable code
- Follow best practices for Java development
- Make sure to execute the requests ASAP to meet deadlines

## Architecture
- Follow DDD/Hexagonal architecture with domain/, application/, infrastructure/ layers
- Domain entities should be pure Java with Lombok
- Use repository interfaces in domain, implementations in infrastructure

## Code Style
- Use meaningful names in Spanish/English consistently
- Always include validation in DTOs
- Prefer constructor injection over @Autowired

## Testing
- Write unit tests for domain logic
- Integration tests for repositories