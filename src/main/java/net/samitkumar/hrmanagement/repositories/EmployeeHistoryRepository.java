package net.samitkumar.hrmanagement.repositories;

import net.samitkumar.hrmanagement.models.EmployeeHistory;
import org.springframework.data.repository.ListCrudRepository;

public interface EmployeeHistoryRepository extends ListCrudRepository<EmployeeHistory,Integer> {
}
