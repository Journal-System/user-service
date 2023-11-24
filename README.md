# user-service
Lab 2 - Microservices - User Service
## Description
This is a simple user service that allows you to create, update, delete and get users. It also allows you to get all users and get users by id.

## Getting Started

### Database
If you want to add new data to your database then you have to change spring.jpa.hibernate.ddl-auto property to `create-drop`. After you have added the data you have to change it back to `update`. 

Otherwise the application will crash because it tries to create the tables again and the tables already exist.

### Docker
1. Run `mvn clean install` to build the project
2. Run `./mvnw package` to build the project
3. Run `docker-compose up --build` to build the project

### Swagger
1. Run the application
2. Go to `http://localhost:8081/swagger-ui/index.html#/` to view the API documentation

### Authors
* Mikael Söderström
* Nuh Jama