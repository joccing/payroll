# A Simple Demo for CSV upload using Spring Boot

## Objective
- We want to create two REST endpoints to support the query and storage of employee data.
- Data includes Name and Salary
- The two endpoints are:
  /users	supports query parameters for min, max, offset, limit and sort
  /upload	supports multipart/form data for CSV
- Implementation Specifics
1. Spring Boot
2. Java
3. Shared through Github
4. Validation logic for data and CSV file format

## Architectural Design

Refer these [slides](https://docs.google.com/presentation/d/1NWX_vBVJh4836nv_FTgzX9EZ2JB2bVpMVZ7TrYJ7Cv0/edit#slide=id.g17c265d9ea7_0_114).

## Technical Stack

Maven project structure generated with default dependency using Spring Initializr
Package structure follows MVC model
Dependencies
- Java 17
- MySQL 8.0.27
- Spring Boot Web
- openCSV 5.4
- Jasypt Parameter Encryptor
- Apache Commons Lang3 3.12
- Maven 3.8.6

## Installation Instructions

Download to your machine
```sh
$ git clone https://github.com/joccing/payroll.git
$ cd payroll
```
Amend **root** user's **spring.datasource.password** under resources/application.properties to your own password

Run Maven
```sh
./mvnw compile
./mvnw clean package
./mvnw spring-boot:run
```

## License
MIT


