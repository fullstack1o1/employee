package net.samitkumar.employee.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record EmployeeRequest(
        Integer employeeId,
        String empNo,
        String firstName, //notnull
        String lastName, //notnull
        String email, //notnull
        String phoneNumber,
        @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate hireDate, //notnull
        @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate, //notnull
        Integer jobId,
        Double salary,
        Integer departmentId) {
}
