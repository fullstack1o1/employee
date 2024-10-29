package net.samitkumar.employee.repositories;

import net.samitkumar.employee.models.JobTitle;
import org.springframework.data.repository.ListCrudRepository;

public interface JobTitleRepository extends ListCrudRepository<JobTitle, Integer> {
}
