# Public API Scenario Runner

API automation framework using Java, Serenity BDD, REST Assured, and JUnit 5.
Target API: [Restful Booker](https://restful-booker.herokuapp.com/apidoc/index.html)

---

## Prerequisites

- Java 17
- Maven 3.8+





---

## Run the tests

```bash
mvn clean verify
```

---

## View the report

After running, open:

```
target/site/serenity/index.html
```

---

## Architecture

```
src/test/java/
│
├── tests/        → Test scenarios (JUnit 5)
├── steps/        → Serenity @Step methods + assertions
├── clients/      → REST Assured HTTP requests
└── support/      → ScenarioContext (token, bookingId) + ConfigReader

src/test/resources/
├── payloads/     → JSON request bodies
```

Each layer has one responsibility. Tests describe the scenario. Steps name the actions and run assertions. Clients build and send the HTTP requests. Support classes hold shared utilities and runtime values.

---

## Scenario: Public API Smoke Flow

1. Auth — valid and invalid
2. Create Booking
3. Get Booking by ID
4. Update Booking
5. Delete Booking
6. Get Booking Not Found

---
## How runtime variables are captured and reused

Two values are shared across steps during a test run: `token` and `bookingId`. Both are stored in `ScenarioContext`, a class with static fields accessible from any step.

- After the Auth step, the token is extracted from the response and stored via `ScenarioContext.setToken()`.
- After the Create Booking step, the bookingId is extracted from the response and stored via `ScenarioContext.setBookingId()`.
- All subsequent steps (Get, Update, Delete, Not Found) retrieve these values via `ScenarioContext.getToken()` and `ScenarioContext.getBookingId()` without any manual passing between methods.



