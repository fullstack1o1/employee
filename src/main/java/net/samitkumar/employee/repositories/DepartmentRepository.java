package net.samitkumar.employee.repositories;

import net.samitkumar.employee.models.Department;
import org.springframework.data.repository.ListCrudRepository;

public interface DepartmentRepository extends ListCrudRepository<Department, Integer> {
}
