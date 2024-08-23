package net.samitkumar.hrmanagement.repositories;

import net.samitkumar.hrmanagement.models.Employee;
import org.springframework.data.repository.ListCrudRepository;

public interface EmployeeRepository extends ListCrudRepository<Employee, Integer> {
}
