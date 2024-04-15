# BANKREST API
The project is a server-side application for managing bank deposits, implemented in Java using Spring Boot, JPA, and PostgreSQL. The project includes an API for working with clients, organizational and legal forms, banks, and deposits, as well as providing creating, editing, and deleting functionalities for entities.

## Requirements

- Java 17+
- Spring Boot 3.2.4
- Maven for building
- PostgreSQL for data storage
- Docker (optional)

The application will be available at: `http://localhost:8080`

## Using the API

The API provides the following endpoints:

- `GET /client`: Get a list of all clients
- `GET /client/{id}`: Get a client by ID
- `POST /client`: Create a new client
- `PUT /client/{id}`: Update client information
- `DELETE /client/{id}`: Delete a client

Similar endpoints are available for forms, banks and deposits.
