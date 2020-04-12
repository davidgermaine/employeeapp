INSERT INTO fields (id, "name", "type")
        VALUES ('123e4567-e89b-12d3-a456-426655440000', 'Java', 'Software Development');
        
INSERT INTO skills (id, field, experience, summary)
        VALUES ('123e4567-e89b-12d3-a456-426655440000', '123e4567-e89b-12d3-a456-426655440000', 0, 'Worked with mulit-threading and generics');
        
INSERT INTO addresses (id, street, suite, city, region, postal, country)
        VALUES ('123e4567-e89b-12d3-a456-426655440000', '123 Main St.', 'Apt. 456', 'Detroit', 'MI', '48201', 'US');
        
INSERT INTO employees (id, firstname, lastname, address, contactemail, companyemail, birthdate, hireddate, "role", businessunit, assignedto)
        VALUES ('123e4567-e89b-12d3-a456-426655440000', 'John', 'Doe', '123e4567-e89b-12d3-a456-426655440000', 'johndoe@example.com', 
                'john.doe@perficient.com', 'string', 'string', 'Technical Consultant', 'Digital Experience Group', '');
                
INSERT INTO employee_skills (employee, skill)
        VALUES ('123e4567-e89b-12d3-a456-426655440000', '123e4567-e89b-12d3-a456-426655440000');