BEGIN TRANSACTION;

DROP TABLE IF EXISTS fields;

CREATE TABLE fields (
  id uuid PRIMARY KEY,
  name varchar(255) NOT NULL UNIQUE,
  type varchar(255) NOT NULL
  --role varchar(255) NOT NULL default('user')
);

COMMIT TRANSACTION;

BEGIN TRANSACTION;

DROP TABLE IF EXISTS skills;
CREATE TABLE skills (
        id uuid PRIMARY KEY,
        field uuid NOT NULL,
        experience integer NOT NULL default(0),
        summary text,
        
        CONSTRAINT FK_fields FOREIGN KEY (field) references fields (id)
);

COMMIT TRANSACTION;
        
BEGIN TRANSACTION;

DROP TABLE IF EXISTS addresses;
CREATE TABLE addresses (
        id uuid PRIMARY KEY,
        street varchar(100) NOT NULL,
        suite varchar(100),
        city varchar(100) NOT NULL,
        region varchar(3) NOT NULL,
        postal varchar(10) NOT NULL,
        country varchar(2) NOT NULL     
);

COMMIT TRANSACTION;   

BEGIN TRANSACTION;    
        
DROP TABLE IF EXISTS employees;
CREATE TABLE employees (
        
        id uuid PRIMARY KEY,
        firstname varchar(50) NOT NULL,
        lastname varchar(50) NOT NULL,
        address uuid NOT NULL,
        contactemail varchar(100),
        companyemail varchar(100) NOT NULL,
        birthdate varchar(20) NOT NULL,
        hireddate varchar(20) NOT NULL,
        "role" varchar(50) NOT NULL,
        businessunit varchar(50),
        assignedto uuid,
        
        CONSTRAINT FK_addresses FOREIGN KEY (address) references addresses (id),
        CONSTRAINT FK_employees FOREIGN KEY (assignedto) references employees (id)        
);

COMMIT TRANSACTION;      

BEGIN TRANSACTION;    
        
DROP TABLE IF EXISTS employees_skills;
CREATE TABLE employee_skills (
        id uuid PRIMARY KEY,
        employee uuid NOT NULL,        
        skill uuid NOT NULL,
        
        CONSTRAINT FK_employees FOREIGN KEY (employee) references employees (id),
        CONSTRAINT FK_skills FOREIGN KEY (skill) references skills (id)       
);

COMMIT TRANSACTION;