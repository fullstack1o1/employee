package net.samitkumar.employee.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("job_titles")
public record JobTitle(
        @Id Integer jobId,
        String title,
        Double minSalary,
        Double maxSalary) {
}
