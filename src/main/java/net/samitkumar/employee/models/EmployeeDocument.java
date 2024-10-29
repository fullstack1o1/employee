package net.samitkumar.employee.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("employee_documents")
public record EmployeeDocument(
        @Id Integer documentId,
        String documentName,
        byte[] documentContent,
        Integer employeeId) {
}
