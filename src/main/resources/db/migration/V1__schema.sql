CREATE TABLE IF NOT EXISTS departments (
     department_id SERIAL PRIMARY KEY,
     department_name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS job_titles (
        job_id SERIAL PRIMARY KEY,
        title VARCHAR(100) UNIQUE NOT NULL,
        min_salary DECIMAL(15, 2),
        max_salary DECIMAL(15, 2)
);

CREATE SEQUENCE emp_seq START 100;

CREATE TABLE IF NOT EXISTS employees (
       employee_id SERIAL PRIMARY KEY,
       emp_no VARCHAR(50) DEFAULT ('min' || nextval('emp_seq')),
       first_name VARCHAR(50) NOT NULL,
       last_name VARCHAR(50) NOT NULL,
       email VARCHAR(100) UNIQUE NOT NULL,
       phone_number VARCHAR(20),
       hire_date DATE NOT NULL,
       salary DECIMAL(15, 2),
       manager_id INT REFERENCES employees(employee_id),
       job_id INT NOT NULL REFERENCES job_titles(job_id),
       department_id INT NOT NULL REFERENCES departments(department_id)
);

CREATE TABLE IF NOT EXISTS employee_history (
      history_id SERIAL PRIMARY KEY,
      employee_id INT NOT NULL REFERENCES employees(employee_id),
      start_date DATE NOT NULL,
      end_date DATE,
      job_id INT NOT NULL REFERENCES job_titles(job_id),
      department_id INT NOT NULL REFERENCES departments(department_id)
);

CREATE TABLE IF NOT EXISTS employee_documents (
        document_id SERIAL PRIMARY KEY,
        employee_id INT NOT NULL REFERENCES employees(employee_id),
        document_name VARCHAR(100) NOT NULL,
        document_type VARCHAR(50),
        document_content BYTEA NOT NULL
);

-- INSERT INTO departments (department_name) VALUES ('BOARD OF DIRECTORS');
--
-- INSERT INTO job_titles (title, min_salary, max_salary) VALUES ('Founder', 1000000, 2000000);
--
-- INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, salary, job_id, department_id, manager_id) VALUES ('John','Doe','john.d@min.net','13311331',CURRENT_DATE,1000000, 1, 1, 1);
--
-- INSERT INTO employee_history (employee_id, start_date, end_date, job_id, department_id) VALUES (1, CURRENT_DATE, '9999-01-01', 1, 1);
