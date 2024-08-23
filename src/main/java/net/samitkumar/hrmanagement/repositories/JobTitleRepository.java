package net.samitkumar.hrmanagement.repositories;

import net.samitkumar.hrmanagement.models.JobTitle;
import org.springframework.data.repository.ListCrudRepository;

public interface JobTitleRepository extends ListCrudRepository<JobTitle, Integer> {
}
