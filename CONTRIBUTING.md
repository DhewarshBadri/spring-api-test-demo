# Contributing to Spring API Test Demo

Thank you for your interest in contributing to this project! This document provides guidelines and instructions for contributing.

## Development Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/spring-api-test-demo.git
   cd spring-api-test-demo
   ```

2. Set up the project with Maven:
   ```bash
   mvn clean install
   ```

3. Run tests locally:
   ```bash
   mvn test
   ```

## Branching Strategy

- `main` - Production-ready code
- `develop` - Integration branch for features
- `feature/*` - For new features
- `bugfix/*` - For bug fixes
- `hotfix/*` - For urgent production fixes

## Pull Request Process

1. Create a new branch from `develop` (for features/fixes) or `main` (for hotfixes)
2. Make your changes
3. Write or update tests as necessary
4. Ensure all tests pass locally
5. Push your branch and create a pull request
6. Fill out the pull request template with all required information

## Testing Guidelines

1. All new features should include appropriate tests
2. Tests should be placed in the appropriate test directory
3. Both unit and integration tests should be written when applicable
4. Follow the existing test style and patterns
5. See [TESTING.md](TESTING.md) for detailed information about our testing framework

## Code Style

- Follow the existing code style in the project
- Use meaningful variable and method names
- Include comments for complex logic
- Keep methods small and focused on a single responsibility
- Use appropriate design patterns

## Commit Messages

Follow these guidelines for commit messages:

- Use the present tense ("Add feature" not "Added feature")
- Use the imperative mood ("Move cursor to..." not "Moves cursor to...")
- Limit the first line to 72 characters or less
- Reference issues and pull requests after the first line

Example:
```
Add TestNG configuration for integration tests

- Create standalone-testng.xml with integration test classes
- Update TestReportListener to generate Maven-compatible reports
- Fix issue #123
```

## Questions or Problems?

If you have any questions or encounter problems, please open an issue in the repository.

Thank you for contributing!
