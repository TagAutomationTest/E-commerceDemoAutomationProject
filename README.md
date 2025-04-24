# About-Framework

This is an automation framwork created to automate user scenarios for Demo E-coomerce Website

### Technologies Used:

- **Language(s)**: Java
- **Frameworks**: Selenium, TestNG ,Allure ,Log4j ,data driven from (Json and perperities files)
- **Libraries**:important libraries or dependencies used in the project.

   <properties>

        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

        <java.version>21</java.version>

        <maven-surefire.version>3.5.3</maven-surefire.version>

        <log4j.version>2.24.3</log4j.version>

        <allure.version>2.29.1</allure.version>

        <aspectj.version>1.9.23</aspectj.version>

        <selenium.version>4.31.0</selenium.version>

        <testNG.version>7.11.0</testNG.version>

        <commons-io.version>2.18.0</commons-io.version>

        <json-simple.version>1.1.1</json-simple.version>

        <json-path.version>2.9.0</json-path.version>

    </properties>

## Table of Contents

- [Features](#features)
- [Installation](#installation)

---

## Features

- **Feature 1**: Maven project built
- **Feature 2**: Selenium4
- **Feature 3**: Page object design pattern using fluent pattern
- **Feature 4**: Allure report
- **Feature 5**: Log4j
- **Feature 6**: TestNG Listeners
- **Feature 7**: read data from properities files
- **Feature 8**:  read test data from json

---

## Installation

### Prerequisites:

- [Java](https://www.guru99.com/install-java.html) should be installed and configured.
- [Maven](https://mkyong.com/maven/how-to-install-maven-in-windows/) should be installed and configured.
- Download the files from Git repository either as zip file OR
  using [Git](https://phoenixnap.com/kb/how-to-install-git-windows).
- Download and install [Allure](https://allurereport.org/docs/install-for-windows/) commandline application, suitable
  for your environment.

### Steps to Install:

1. Clone the repository:
    ```bash
    git clone [https://github.com/TagAutomationTest/E-commerceDemoAutomationProject.git]
    ```
2. Navigate into the project directory, Run below commands to Install dependencies.
    ```bash
     mvn clean install
    ```
3. Run tests using below command.
    ```bash
    mvn test
    ```
4. Allure Report: To generate the report we need to go through below steps.

```bash
allure serve test-outputs/allure-results
 
---

