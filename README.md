# Selenium Test Automation Framework – Page Object Model

## Java | Selenium WebDriver | TestNG | Maven

## Overview

This repository contains a Selenium Test Automation Framework developed using Java, TestNG, and Maven, following the
Page Object Model (POM) design pattern. The framework is built with a strong focus on scalability, maintainability, and
CI/CD readiness, aligning with real-world SDET responsibilities.

## Key Responsibilities Covered (SDET Aligned)

-Test automation framework design and implementation
-Page Object Model architecture
-Test execution using TestNG
-Maven-based build and dependency management
-Failure handling and screenshot capture
-Logging and reporting integration
-Version control using Git
-CI/CD-ready test execution

## Tech Stack

-Programming Language: Java 11
-Automation Tool: Selenium WebDriver
-Test Framework: TestNG
-Build Tool: Maven
-Reporting: Extent Reports
-Logging: Log4j
-Version Control: Git
-OS: Windows / Linux compatible

## Framework Design

-Base Test class for WebDriver initialization and teardown
-Page classes for UI interaction logic
-Test classes for business-level test scenarios
-Utility classes for waits, screenshots, and reusable methods
-Listener implementation for reporting and test lifecycle handling

## Project Structure

src
├── main
│   └── java
│       ├── base
│       ├── pages
│       ├── util
│       └── config
└── test
└── java
└── testcases

testng.xml
pom.xml
.gitignore

## Features

-Page Object Model (POM) implementation
-Cross-browser test execution support
-Automated screenshot capture on test failure
-Extent HTML reporting
-TestNG XML suite execution
-Clean Git repository (no build artifacts committed)
-Maven Surefire plugin integration

## How to Execute Tests

### Clone Repository
git clone https://github.com/DeveshSingh70/PageObjectModel.git
cd selenium-pom-framework
### Run tests using Maven
  mvn clean test
### Run using TestNG suite
  mvn clean test -DsuiteXmlFile=testng.xmlgit status

## CI/CD Compatibility
-Jenkins
-GitHub Actions
-Any CI tool supporting Maven builds

## Author

###Devesh
SDET | Automation Testing | Java Selenium




