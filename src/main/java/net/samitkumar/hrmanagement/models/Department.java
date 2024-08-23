package net.samitkumar.hrmanagement.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("departments")
public record Department(
        @Id Integer departmentId,
        String departmentName) {
}
