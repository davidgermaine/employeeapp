INSERT INTO fields (id, "name", "type")
        VALUES ('123e4567-e89b-12d3-a456-426655440000', 'Java', 'Software Development');
INSERT INTO fields (id, "name", "type")
        VALUES ('00000000-0000-0000-0000-000000000000', 'C#', 'Software Development');
INSERT INTO fields (id, "name", "type")
        VALUES ('11111111-1111-1111-1111-111111111111', 'Python', 'Data Analysis');
INSERT INTO fields (id, "name", "type")
        VALUES ('22222222-2222-2222-2222-222222222222', 'Java', 'Front-end QA');
INSERT INTO fields (id, "name", "type")
        VALUES ('33333333-3333-3333-3333-333333333333', 'SQL', 'Database Design');
        
INSERT INTO skills (id, field, experience, summary)
        VALUES ('123e4567-e89b-12d3-a456-426655440000', '123e4567-e89b-12d3-a456-426655440000', 60, 'Worked with mulit-threading and generics');
INSERT INTO skills (id, field, experience, summary)
        VALUES ('99999999-9999-9999-9999-999999999999', '00000000-0000-0000-0000-000000000000', 1, 'Self-taught from YouTube');
INSERT INTO skills (id, field, experience, summary)
        VALUES ('88888888-8888-8888-8888-888888888888', '11111111-1111-1111-1111-111111111111', 6, 'Computational programming taught in college, used to build wave simulations');
INSERT INTO skills (id, field, experience, summary)
        VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '22222222-2222-2222-2222-222222222222', 24, 'Used Spring Boot and VueJs to develop dynamic user interfaces for web applications');
INSERT INTO skills (id, field, experience, summary)
        VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '33333333-3333-3333-3333-333333333333', 30, 'Designed relational database systems for small startup businesses to track employee skills and information');
        
INSERT INTO addresses (id, street, suite, city, region, postal, country)
        VALUES ('123e4567-e89b-12d3-a456-426655440000', '123 Main St.', 'Apt. 456', 'Detroit', 'MI', '48201', 'US');
INSERT INTO addresses (id, street, suite, city, region, postal, country)
        VALUES ('44444444-4444-4444-4444-444444444444', '9029 West Ave.', '', 'Royal Oak', 'MI', '48074', 'US');
INSERT INTO addresses (id, street, suite, city, region, postal, country)
        VALUES ('55555555-5555-5555-5555-555555555555', '10200 College Ave.', 'Apt. 24', 'Appleton', 'WI', '54911', 'US');
INSERT INTO addresses (id, street, suite, city, region, postal, country)
        VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', '11111 Corporate Blv.', '', 'Springfield', 'OH', '00000', 'US');
        
INSERT INTO employees (id, firstname, lastname, address, contactemail, companyemail, birthdate, hireddate, "role", businessunit, assignedto)
        VALUES ('123e4567-e89b-12d3-a456-426655440000', 'John', 'Doe', '123e4567-e89b-12d3-a456-426655440000', 'johndoe@example.com', 
                'john.doe@company.com', 'string', 'string', 'Technical Consultant', 'Digital Experience Group', '');
INSERT INTO employees (id, firstname, lastname, address, contactemail, companyemail, birthdate, hireddate, "role", businessunit, assignedto)
        VALUES ('66666666-6666-6666-6666-666666666666', 'Percy', 'Jackson', '44444444-4444-4444-4444-444444444444', '', 
                'percy.jackson@company.com', '2004-04-01', '2020-03-14', 'Project Manager', '', '77777777-7777-7777-7777-777777777777');
INSERT INTO employees (id, firstname, lastname, address, contactemail, companyemail, birthdate, hireddate, "role", businessunit, assignedto)
        VALUES ('77777777-7777-7777-7777-777777777777', 'Jane', 'Doe', '55555555-5555-5555-5555-555555555555', 'janedoe@example.com', 
                'jane.doe@company.com', '1978-12-21', '2010-09-10', 'Director', 'Adobe', 'dddddddd-dddd-dddd-dddd-dddddddddddd');
INSERT INTO employees (id, firstname, lastname, address, contactemail, companyemail, birthdate, hireddate, "role", businessunit, assignedto)
        VALUES ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'Chris', 'Evenson', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 'coolkid89@outdated.com', 
                'chris.evenson@company.com', '1989-08-05', '2015-09-10', 'Chief', '', '');
                
INSERT INTO employee_skills (employee, skill)
        VALUES ('123e4567-e89b-12d3-a456-426655440000', '123e4567-e89b-12d3-a456-426655440000');
INSERT INTO employee_skills (employee, skill)
        VALUES ('66666666-6666-6666-6666-666666666666', '99999999-9999-9999-9999-999999999999');
INSERT INTO employee_skills (employee, skill)
        VALUES ('66666666-6666-6666-6666-666666666666', '88888888-8888-8888-8888-888888888888');
INSERT INTO employee_skills (employee, skill)
        VALUES ('77777777-7777-7777-7777-777777777777', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa');
INSERT INTO employee_skills (employee, skill)
        VALUES ('77777777-7777-7777-7777-777777777777', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb');