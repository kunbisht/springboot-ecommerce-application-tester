# Contributing to Spring Boot E-commerce Application

We love your input! We want to make contributing to this project as easy and transparent as possible, whether it's:

- Reporting a bug
- Discussing the current state of the code
- Submitting a fix
- Proposing new features
- Becoming a maintainer

## We Develop with GitHub

We use GitHub to host code, to track issues and feature requests, as well as accept pull requests.

## We Use [Trunk-based Development](https://trunkbaseddevelopment.com/)

Pull requests are the best way to propose changes to the codebase. We actively welcome your pull requests:

1. Fork the repo and create your branch from `main`.
2. Create a feature branch: `feature/feature-name`
3. If you've added code that should be tested, add tests.
4. If you've changed APIs, update the documentation.
5. Ensure the test suite passes.
6. Make sure your code lints.
7. Issue that pull request!

## Branch Naming Convention

- `feature/feature-name` - for new features
- `feature/bugfix-name` - for bug fixes
- `feature/hotfix-name` - for critical fixes

## Any contributions you make will be under the MIT Software License

In short, when you submit code changes, your submissions are understood to be under the same [MIT License](http://choosealicense.com/licenses/mit/) that covers the project. Feel free to contact the maintainers if that's a concern.

## Report bugs using GitHub's [issues](https://github.com/kunbisht/springboot-ecommerce-application-tester/issues)

We use GitHub issues to track public bugs. Report a bug by [opening a new issue](https://github.com/kunbisht/springboot-ecommerce-application-tester/issues/new); it's that easy!

## Write bug reports with detail, background, and sample code

**Great Bug Reports** tend to have:

- A quick summary and/or background
- Steps to reproduce
  - Be specific!
  - Give sample code if you can
- What you expected would happen
- What actually happens
- Notes (possibly including why you think this might be happening, or stuff you tried that didn't work)

## Development Process

1. Clone the repository
2. Create a feature branch from `main`
3. Make your changes
4. Add or update tests as needed
5. Run the full test suite: `mvn clean test`
6. Run code quality checks: `mvn clean verify`
7. Commit your changes with a descriptive message
8. Push to your feature branch
9. Create a Pull Request

## Code Style

- Follow Java coding conventions
- Use meaningful variable and method names
- Write comprehensive tests for new functionality
- Document public APIs
- Keep methods small and focused
- Use Spring Boot best practices

## Testing

- Write unit tests for all new functionality
- Ensure existing tests continue to pass
- Aim for high test coverage
- Use meaningful test names that describe what is being tested

## Pull Request Process

1. Ensure any install or build dependencies are removed before the end of the layer when doing a build.
2. Update the README.md with details of changes to the interface, this includes new environment variables, exposed ports, useful file locations and container parameters.
3. Increase the version numbers in any examples files and the README.md to the new version that this Pull Request would represent.
4. Your Pull Request will be merged once you have the sign-off of at least one maintainer.

## License

By contributing, you agree that your contributions will be licensed under its MIT License.

## References

This document was adapted from the open-source contribution guidelines for [Facebook's Draft](https://github.com/facebook/draft-js/blob/a9316a723f9e918afde44dea68b5f9f39b7d9b00/CONTRIBUTING.md)