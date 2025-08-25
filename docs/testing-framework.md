# API Testing Framework

This document provides an overview of the API testing framework implemented in this project.

## Overview

The testing framework in this project is designed to:

1. Automatically test the Spring Boot REST API endpoints
2. Generate detailed HTML and XML test reports
3. Send reports via email to stakeholders
4. Notify team members through Microsoft Teams
5. Run on a scheduled basis using GitHub Actions

## Components

### Test Framework

- **TestNG**: The primary testing framework
- **RestAssured**: Library for testing REST APIs
- **Spring Boot Test**: For integration testing with Spring Boot

### Report Generation

- **TestReportListener**: Custom TestNG listener that generates HTML reports
- **Maven Surefire Reports**: XML reports compatible with CI/CD tools

### Automation

- **GitHub Actions**: CI/CD pipeline for scheduled and manual test execution
- **SendGrid**: Email delivery service for reports
- **Microsoft Teams**: Team notification integration

## Getting Started

### Running Tests Locally

To run the tests locally:

```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=BalanceApiTest

# Run tests with a specific TestNG XML file
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Viewing Test Reports

After running tests, reports can be found in:

- TestNG HTML Reports: `test-output/index.html`
- Maven Surefire Reports: `target/surefire-reports/index.html`
- Maven Site Reports: `target/site/surefire-report.html`

### Adding New Tests

To add a new API test:

1. Create a new test class in `src/test/java/com/example/demo/api`
2. Extend `AbstractTestNGSpringContextTests` for Spring Boot integration
3. Add TestNG annotations (`@Test`, `@BeforeClass`, etc.)
4. Use RestAssured to make API calls and assertions
5. Add the test class to `src/test/resources/testng.xml`

Example:

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewApiTest extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    private int port;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testEndpoint() {
        given()
            .pathParam("id", "123")
        .when()
            .get("/api/resource/{id}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo("123"));
    }
}
```

## CI/CD Integration

This project uses GitHub Actions for CI/CD. The workflow is defined in `.github/workflows/daily-api-tests.yml`.

### Scheduled Runs

Tests are automatically run daily at 9:00 AM UTC. This schedule can be modified in the workflow file.

### Manual Trigger

Tests can also be triggered manually from the GitHub Actions tab with the option to send email reports.

## Notifications

### Email Reports

Test reports are sent via email using SendGrid. See [email-integration.md](email-integration.md) for setup instructions.

### Microsoft Teams

Test results are posted to Microsoft Teams. See [teams-integration.md](teams-integration.md) for setup instructions.

## Extending the Framework

### Adding Custom Report Sections

To add custom sections to the test reports, modify the `TestReportListener` class.

### Configuring Test Execution

Test execution can be configured in:

- `pom.xml`: Maven configuration
- `src/test/resources/testng.xml`: TestNG test suite configuration
- `.github/workflows/daily-api-tests.yml`: GitHub Actions workflow
