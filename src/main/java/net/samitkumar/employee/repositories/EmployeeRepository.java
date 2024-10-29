package net.samitkumar.employee.repositories;

import net.samitkumar.employee.models.Employee;
import org.springframework.data.repository.ListCrudRepository;

public interface EmployeeRepository extends ListCrudRepository<Employee, Integer> {
}
