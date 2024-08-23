package net.samitkumar.hrmanagement.repositories;

import net.samitkumar.hrmanagement.models.EmployeeDocument;
import org.springframework.data.repository.ListCrudRepository;

public interface EmployeeDocumentRepository extends ListCrudRepository<EmployeeDocument, Integer> {
}
