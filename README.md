# employee

api to manage employees record in a company

```shell
#new department
http :8080/api/department departmentName="IT"

#new job title
http :8080/api/jobtitle title="Engineer" minSalary=2000 maxSalary=5000

#new employee with multipart form data
http --multipart POST :8080/api/employee firstName="St" lastName="Pt" email="s.t@test.net" hireDate="2025-08-01" startDate="2025-08-15" jobId=1 departmentId=1 salary=3500
http --multipart POST :8080/api/employee firstName="St1" lastName="Pt1" email="s1.t1@test.net" hireDate="2025-08-01" startDate="2025-08-15" jobId=1 departmentId=1 salary=3500

#new employee with json data
http :8080/api/employee firstName="St2" lastName="Pt2" email="s2.t2@test.net" hireDate="2025-08-01" startDate="2025-08-15" jobId=1 departmentId=1 salary=3500

http :8080/api/employee
```