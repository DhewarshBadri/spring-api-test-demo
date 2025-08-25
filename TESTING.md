# Test Documentation

This document provides information about the test framework setup and how to run tests in this project.

## Test Framework Overview

The project uses TestNG as the primary testing framework with the following features:
- TestNG for test structure and execution
- RestAssured for API testing
- Custom test listeners for reporting
- Integration with Maven for both local and CI/CD test execution

## Running Tests

### Local Testing

You can run tests in several ways:

#### Using Maven
```bash
# Run all tests
mvn clean test

# Run a specific test class
mvn test -Dtest=SimpleTest

# Run a specific test suite
mvn test -DsuiteXmlFile=src/test/resources/standalone-testng.xml
```

#### Using IDE
You can run individual test classes or methods directly from your IDE by right-clicking on the test class/method and selecting "Run".

### GitHub Actions CI/CD

Tests automatically run on GitHub Actions:
- On a schedule (daily at 9:00 AM UTC)
- When manually triggered from the Actions tab

## Test Report Structure

### HTML Reports
The custom `TestReportListener` generates HTML reports that include:
- Test execution summary (total, passed, failed, skipped)
- Test case details by suite
- Execution time and date

These reports are located in:
- `test-output/` (TestNG's default location)
- `target/surefire-reports/` (Maven's expected location)

### XML Reports
XML reports compatible with Maven Surefire are generated in:
- `target/surefire-reports/`

These reports follow Maven's naming convention for test reports.

## Troubleshooting Common Issues

### "Class not found" Errors
If you encounter "Class not found" errors:
1. Ensure the class exists in the correct package
2. Run `mvn clean compile test-compile` to make sure all test classes are compiled
3. Check XML test suite files for correct class references

### Report Generation Issues
If test reports aren't generated:
1. Make sure the TestNG listener is properly configured in both the XML test suites and Maven pom.xml
2. Check if the appropriate directories exist or have write permissions
3. Run with verbose logging: `mvn test -Dtestng.verbose=10`
