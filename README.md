# Spring-Mongodb-Jwt-Docker Developed by Luiz Philipe

Swagger: API Documentation and to make requests to add users and Token (Jwt) into database using MongoDb

http://localhost:server.port/serasa/swagger-ui.html#/

Auth Api 

User-Controller


# BACK-END
Devloped using Spring Boot + JWT Authentication at back-end

# Spring + JWT Auth with Spring Security + MongoDb + Docker

## Dependencies

```
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```
```
<groupId>io.jsonwebtoken</groupId>
<artifactId>jjwt</artifactId>
<version>0.9.1</version>
```
```
<groupId>io.springfox</groupId>
<artifactId>springfox-swagger2</artifactId>
<version>2.9.2</version>

<groupId>io.springfox</groupId>
<artifactId>springfox-swagger-ui</artifactId>
<version>2.9.2</version>

```

## Docker

Use the command "docker-compose up" in the terminal to run Docker and configure de file docker-compose.yml

```
version: '3.9'
services:
  mongodb:
    image: mongo:5.0
    ports:
      - 27017:27017
    volumes:
      - ~/apps/mongo:/data/db
    environment:
      - MONGO_INITDB_ROOT_USERNAME=admin
      - MONGO_INITDB_ROOT_PASSWORD=admin
      - MONGO_INITDB_ROOT_DB=admin
```

## Configure Spring Datasource, JPA, App properties
Open `src/main/resources/application.properties`
- 
- For MongoDb:

```
spring.data.mongodb.authentication-database=admin
spring.data.mongodb.username=admin
spring.data.mongodb.password=admin
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=admin

```
- For JWT:

```
security.jwt.token.expire-length=3600000
security.jwt.token.secret-key=secret
```

## Run Spring Boot application
CovidSerasaApplication


