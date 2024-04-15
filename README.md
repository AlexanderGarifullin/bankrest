# BANKREST API
The project is a server-side application for managing bank deposits, implemented in Java using Spring Boot, JPA, and PostgreSQL. The project includes an API for working with clients, organizational and legal forms, banks, and deposits, as well as providing sorting, creating, editing, and deleting functionalities for entities.

## Requirements

- Java 17+
- Spring Boot 3.2.4
- Maven for building
- PostgreSQL for data storage
- Docker (optional)

The application will be available at: `http://localhost:8080`

## Models

### Bank

Represents a bank entity.

- **id** (int, primary key): The unique identifier for the bank.
- **name** (varchar(255), not null, unique): The name of the bank.
- **bik** (varchar(9), not null, unique): The Bank Identification Code (BIK) of the bank. BIK must be a 9-digit number.

### Organizational Legal Form

Represents an organizational legal form entity.

- **id** (int, primary key): The unique identifier for the organizational legal form.
- **name** (varchar(100), not null, unique): The name of the organizational legal form.

### Client

Represents a client entity.

- **id** (int, primary key): The unique identifier for the client.
- **name** (varchar(255), not null, unique): The name of the client.
- **shortName** (varchar(50), null, unique): The short name or abbreviation of the client.
- **address** (varchar(255), not null): The address of the client. Address should be in this format: County, City, Index, Street, Home.
- **organizationalLegalFormId** (int, not null, foreign key): The ID of the organizational legal form associated with the client.

### Deposit

Represents a deposit entity.

- **id** (int, primary key): The unique identifier for the deposit.
- **clientId** (int, not null, foreign key): The ID of the client associated with the deposit.
- **bankId** (int, not null, foreign key): The ID of the bank associated with the deposit.
- **openingDate** (date, not null): The date when the deposit was opened.
- **interestRate** (decimal(5,2), not null): The interest rate of the deposit.
- **termMonths** (int, not null): The term of the deposit in months.

These models represent the entities in the bank_rest database, including banks, organizational legal forms, clients, and deposits. They serve as the basis for the API endpoints and functionalities provided by the application.

## Database Initialization and Population

The database for the BANKREST API is initialized and populated using the following files:

- **dbinit.sql**: This file contains the SQL commands to create the initial structure of the database. It sets up the necessary tables and constraints.
- **dbfill.sql**: After the database structure is created, this file is used to populate the database with sample data. It inserts predefined values into the tables to provide some initial data for testing and development purposes.

Both files are essential for setting up the database environment required for running the BANKREST API. They ensure that the database is properly configured and contains the necessary data to support the functionality of the application.


## Using the API

The API provides the following endpoints:

- `GET /client`: Get a list of all clients
- `GET /client?sort=id`: Get a list of all clients sorted by their id (you can sort by any field of the model)
- `GET /client/{id}`: Get a client by ID
- `POST /client`: Create a new client
- `PUT /client/{id}`: Update client information
- `DELETE /client/{id}`: Delete a client

Similar endpoints are available for forms, banks and deposits.
