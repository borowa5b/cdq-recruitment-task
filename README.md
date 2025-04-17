## CDQ RECRUITMENT TASK

### About

Implementation of recruitment task for CDQ company.
Application allows to create / modify / delete / get people.
Create and edit actions creates task which is executed asynchronously.

The slowdown of task processing can be configured using `cdq-recruitment-task.slowdown-in-millis` variable.

Additionally person response from get all and single person endpoints is cached for 1 minute.
This time can be changed via `cdq-recruitment-task.person-cache-time-in-seconds` variable.

### Author

Mateusz Borowski <[borowa5b@gmail.com](mailto:borowa5b@gmail.com)>

### Tech stack

- Gradle 8.13
- JDK 21
- Spring boot 3.4.4
- MongoDB

Libraries: Lombok, jUnit, Mockito, AssertJ, TestContainers, NanoId, Zalando Problem

### Building

The application uses Gradle. Therefore, it can be built by running `gradle clean build` task.

### Requirements to run application bundle

- Docker
- any terminal

### Running bundled

Application can be started as a bundle with database by running command below in the root directory.
Database documents are created automatically while application is running.

```bash
./gradlew clean build && docker-compose up --build
````

### Requirements to run application locally

- Docker
- JDK 21
- any IDE
- MongoDB database - database URI can be configured in `application.properties` file via `spring.data.mongodb.uri`
  property.

### Running locally.

To start the application locally run gradle task `gradle bootRun`.

### Swagger documentation

After starting the application, it has Swagger documentation available at http://localhost:8105/swagger-ui/index.html.