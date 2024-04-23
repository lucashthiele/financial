CREATE TABLE USERS (
    ID int primary key generated always as identity,
    EMAIL varchar(250) not null,
    PASSWORD varchar(500) not null,
    FIRST_NAME varchar(50) not null,
    SURNAME varchar(100) not null,
    BIRTH_DATE date not null,
    PASSWORD_RECOVERY_CODE int
);

CREATE TABLE TRANSACTIONS (
    ID int primary key generated always as identity,
    VALUE numeric(20, 2) not null,
    TYPE varchar(1) check (type in ('C', 'D')) not null,
    origin varchar(100)
)