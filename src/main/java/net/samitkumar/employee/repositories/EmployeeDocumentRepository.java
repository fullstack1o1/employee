package net.samitkumar.employee.repositories;

import net.samitkumar.employee.models.EmployeeDocument;
import org.springframework.data.repository.ListCrudRepository;

public interface EmployeeDocumentRepository extends ListCrudRepository<EmployeeDocument, Integer> {
}
