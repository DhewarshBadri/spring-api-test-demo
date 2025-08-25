# Spring Boot REST API with TestNG and RestAssured Demo

This project demonstrates a Spring Boot application with a simple REST API, automated tests using TestNG and RestAssured, and GitHub Actions workflow for daily test runs and email reporting.

## Project Structure

- `src/main/java`: Source code for the Spring Boot application
  - `controller`: REST API controllers
  - `model`: Data models
- `src/test/java`: Test code
  - `api`: API tests using RestAssured
  - `utils`: Utility classes for reporting and email

## API Endpoints

- GET `/api/getBalance/{accountId}`: Returns balance information for a given account ID

## Setup Instructions

### Prerequisites

- Java 11+
- Maven 3.6+
- Git

### Local Development

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/spring-api-test-demo.git
   cd spring-api-test-demo
   ```

2. Build the project:
   ```
   mvn clean install
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   ```

4. Run the tests:
   ```
   mvn test
   ```

### GitHub Setup and GitHub Actions

1. Create a new repository on GitHub

2. Push your local repository to GitHub:
   ```
   git init
   git add .
   git commit -m "Initial commit"
   git branch -M main
   git remote add origin https://github.com/yourusername/spring-api-test-demo.git
   git push -u origin main
   ```

3. Configure GitHub Secrets for SMTP:
   - Go to your repository on GitHub
   - Navigate to Settings > Secrets and variables > Actions
   - Add the following secrets:
     - `SMTP_SERVER`: Your SMTP server (e.g., smtp.gmail.com)
     - `SMTP_PORT`: Your SMTP port (e.g., 587)
     - `SMTP_USERNAME`: Your SMTP username
     - `SMTP_PASSWORD`: Your SMTP password
     - `EMAIL_SENDER`: Email address to send from
     - `EMAIL_RECIPIENTS`: Comma-separated list of email recipients

## GitHub Actions Workflow

The workflow is configured to:

1. Run daily at 9:00 AM UTC
2. Build and test the application
3. Generate HTML test reports
4. Send email reports to stakeholders

You can also trigger the workflow manually from the Actions tab in GitHub.

## Test Reports

Test reports are generated using a custom TestNG listener and are available:

1. In the GitHub Actions artifacts after each workflow run
2. Via email to the configured recipients after each workflow run

## Notes on Email Configuration

For Gmail or Google Workspace:
- You may need to use an app password instead of your regular password
- Enable "Less secure app access" or use OAuth2 for authentication

For other email providers:
- Check their documentation for SMTP settings and requirements

## Customizing the Email Recipients

To modify the email recipients:
1. Update the `EMAIL_RECIPIENTS` secret in GitHub with a comma-separated list of email addresses
2. The workflow will automatically use the updated recipients
