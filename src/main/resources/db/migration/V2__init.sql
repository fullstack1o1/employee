INSERT INTO departments (department_name) VALUES ('BOARD OF DIRECTORS');

INSERT INTO job_titles (title, min_salary, max_salary) VALUES ('Founder', 1000000, 2000000);

INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, salary, job_id, department_id, manager_id) VALUES ('John','Doe','john.d@min.net','13311331',CURRENT_DATE,1000000, 1, 1, 1);

INSERT INTO employee_history (employee_id, start_date, end_date, job_id, department_id) VALUES (1, CURRENT_DATE, '9999-01-01', 1, 1);
