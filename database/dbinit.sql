-- Create bank_rest database
create database bank_rest;

-- Create Bank table
create table if not exists Bank (
    id int primary key generated always as identity,
    name varchar(255) not null unique,
    bik varchar(9) not null unique check (length(bik)=9)
);

-- Create Organizational_legal_form table
create table if not exists Organizational_legal_form  (
    id int primary key generated always as identity,
    name varchar(100) not null unique
);

-- Create Client table
create table if not exists Client  (
    id int primary key generated always as identity,
    name varchar(255) not null unique,
    short_name varchar(50) null unique,
    address varchar(255) not null,
    organizational_legal_form_id int not null references organizational_legal_form(id) on delete cascade
);

-- Create Deposit table
create table if not exists Deposit  (
    id int primary key generated always as identity,
    client_id int not null references client(id) on delete cascade,
    bank_id int not null references bank(id) on delete cascade,
    opening_date date not null check (opening_date > '2000-01-01'),
    interest_rate decimal(5,2) not null check (interest_rate > 0),
    term_months int not null check (interest_rate > 0)
);


