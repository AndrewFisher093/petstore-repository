# PetStore test automation framework

### Libraries used:

* Java 11
* Maven - project builder
* JUnit5 - testing framework
* RestAssured - library for testing REST services
* AssertJ - fluent assertions java library
* Config - configuration library
* Allure - test report tool
* Jackson_2 - JSON parser for Java 
* javafaker - fake data creation library

### CI/CD

* [Implemented Actions for regression run](https://github.com/AndrewFisher093/petstore-repository/actions)
* [Implemented GitHub Pages with Allure report](https://github.com/AndrewFisher093/petstore-repository/deployments)

### How to run:

To run tests use the Maven command:

- `mvn clean test`

To open results in Allure use the command:

- `allure serve`

![image](https://user-images.githubusercontent.com/16428090/226321617-ddbb9e62-689a-4885-9ea6-126d44dff089.png)
