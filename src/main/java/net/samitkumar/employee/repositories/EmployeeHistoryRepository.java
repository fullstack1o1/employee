package net.samitkumar.employee.repositories;

import net.samitkumar.employee.models.EmployeeHistory;
import org.springframework.data.repository.ListCrudRepository;

public interface EmployeeHistoryRepository extends ListCrudRepository<EmployeeHistory,Integer> {
}
