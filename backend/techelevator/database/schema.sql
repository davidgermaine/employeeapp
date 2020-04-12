BEGIN TRANSACTION;

DROP TABLE IF EXISTS employee_skills;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS skills;
DROP TABLE IF EXISTS fields;

COMMIT TRANSACTION;

BEGIN TRANSACTION;

CREATE TABLE fields (
  id varchar(40) PRIMARY KEY,
  "name" varchar(255) NOT NULL UNIQUE,
  "type" varchar(255) NOT NULL
  --role varchar(255) NOT NULL default('user')
);

COMMIT TRANSACTION;

BEGIN TRANSACTION;

CREATE TABLE skills (
        id varchar(40) PRIMARY KEY,
        field varchar(40) NOT NULL,
        experience integer NOT NULL default(0),
        summary text,
        
        CONSTRAINT FK_fields FOREIGN KEY (field) references fields (id)
);

COMMIT TRANSACTION;
        
BEGIN TRANSACTION;

CREATE TABLE addresses (
        id varchar(40) PRIMARY KEY,
        street varchar(100) NOT NULL,
        suite varchar(100),
        city varchar(100) NOT NULL,
        region varchar(3) NOT NULL,
        postal varchar(10) NOT NULL,
        country varchar(2) NOT NULL     
);

COMMIT TRANSACTION;   

BEGIN TRANSACTION;    
      
CREATE TABLE employees (
        
        id varchar(40) PRIMARY KEY,
        firstname varchar(50) NOT NULL,
        lastname varchar(50) NOT NULL,
        address varchar(40) NOT NULL,
        contactemail varchar(100),
        companyemail varchar(100) NOT NULL,
        birthdate varchar(20) NOT NULL,
        hireddate varchar(20) NOT NULL,
        "role" varchar(50) NOT NULL,
        businessunit varchar(50),
        assignedto varchar(40),
        
        CONSTRAINT FK_addresses FOREIGN KEY (address) references addresses (id)     
);

COMMIT TRANSACTION;      

BEGIN TRANSACTION;    
        
CREATE TABLE employee_skills (
        employee varchar(40) NOT NULL,        
        skill varchar(40) NOT NULL,
        
        CONSTRAINT FK_employees FOREIGN KEY (employee) references employees (id),
        CONSTRAINT FK_skills FOREIGN KEY (skill) references skills (id)       
);

COMMIT TRANSACTION;