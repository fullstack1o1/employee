package net.samitkumar.employee.repositories;

import net.samitkumar.employee.models.Employee;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface EmployeeRepository extends ListCrudRepository<Employee, Integer> {
    List<Employee> findEmployeeByManagerId(Integer managerId);
}
