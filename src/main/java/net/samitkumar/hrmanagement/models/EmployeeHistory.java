package net.samitkumar.hrmanagement.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Table("employee_history")
public record EmployeeHistory(
        @Id Integer historyId,
        @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate,
        @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate,
        Integer jobId,
        Integer departmentId,
        Integer employeeId){}
