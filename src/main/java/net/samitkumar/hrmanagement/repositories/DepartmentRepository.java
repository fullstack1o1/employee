package net.samitkumar.hrmanagement.repositories;

import net.samitkumar.hrmanagement.models.Department;
import org.springframework.data.repository.ListCrudRepository;

public interface DepartmentRepository extends ListCrudRepository<Department, Integer> {
}
