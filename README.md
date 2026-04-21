# Analyst Registry (investment-watchlist)

## Overview

`analyst-registry` is a Java web application built with Spring Boot.  
It manages analysts, firms, users, roles, and capabilities through:

- Spring MVC controllers and Thymeleaf templates
- Spring Data JPA repositories
- Flyway database migrations
- Spring Security authentication/authorization


## Tech Stack

- Language: Java 21 (Gradle toolchain)
- Framework: Spring Boot `4.0.5`
- Build tool / package manager: Gradle Wrapper (`gradlew`, `gradlew.bat`)
- Data layer: Spring Data JPA + MySQL connector
- Database migration: Flyway (`classpath:db/migration`)
- View layer: Thymeleaf
- Security: Spring Security
- Boilerplate reduction: Lombok

## Requirements

- JDK 21
- MySQL (local or remote)
- Network access to download Gradle dependencies from Maven Central

## Entry Points

- Main application class: `src/main/java/pants/pro/analyst_registry/AnalystRegistryApplication.java`
- Main method: `SpringApplication.run(...)`
- Default active profile: `dev` (from `src/main/resources/application.properties`)

## Setup and Run

### 1) Clone and move into the repo

```powershell
git clone <repo-url>
cd investment-watchlist
```

### 2) Configure environment variables

The app resolves DB connection values from environment variables (see [Environment Variables](#environment-variables)).

### 3) Run the app

Windows:

```powershell
.\gradlew.bat bootRun
```

macOS / Linux:

```bash
./gradlew bootRun
```

By default, Spring Boot serves on port `8080` unless overridden.

## Scripts (Gradle Tasks)

Commonly used tasks:

- `bootRun` - Run the Spring Boot app
- `test` - Run tests (JUnit Platform)
- `build` - Compile, test, and package
- `clean` - Remove build outputs
- `bootJar` - Build executable Spring Boot jar
- `bootBuildImage` - Build OCI image

Examples:

```powershell
.\gradlew.bat clean build
.\gradlew.bat test
.\gradlew.bat bootJar
```

To view all tasks:

```powershell
.\gradlew.bat tasks --all
```

## Environment Variables

Defined by `src/main/resources/application-dev.properties`:

- `MYSQL_HOST` (optional, default: `localhost`)
- `MYSQL_PORT` (optional, default: `3306`)
- `MYSQL_DB` (optional, default: `investment-watchlist`)
- `DB_USER` (required)
- `DB_PASSWORD` (required)

Effective JDBC URL format:

`jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DB}?useSSL=true&serverTimezone=UTC`

## Tests

Run tests:

```powershell
.\gradlew.bat test
```

Current test source path:

- `src/test/java/pants/pro/analyst_registry/InvestmentWatchlistApplicationTests.java`

Current behavior: tests load the full Spring context and run Flyway migrations, so a reachable MySQL database is required with valid `DB_USER` and `DB_PASSWORD` (plus optional `MYSQL_HOST`, `MYSQL_PORT`, `MYSQL_DB` overrides).

## Project Structure

```text
.
|- build.gradle
|- settings.gradle
|- src/
|  |- main/
|  |  |- java/pants/pro/analyst_registry/
|  |  |  |- AnalystRegistryApplication.java
|  |  |  |- authentication/
|  |  |  |- controller/
|  |  |  |- service/
|  |  |  |- repository/
|  |  |  |- model/
|  |  |  |- dto/
|  |  |  |- validator/
|  |  |- resources/
|  |     |- application.properties
|  |     |- application-dev.properties
|  |     |- db/migration/
|  |     |- templates/
|  |     |- static/
|  |- test/
|     |- java/pants/pro/analyst_registry/
|- gradlew
|- gradlew.bat
```

## License

This project is licensed under the MIT License. See `LICENSE`.
