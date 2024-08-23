package net.samitkumar.hrmanagement;

import net.samitkumar.hrmanagement.models.*;
import net.samitkumar.hrmanagement.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Testcontainers
@Import(TestcontainersConfiguration.class)
public class RepositoryTest {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    JobTitleRepository jobTitleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeDocumentRepository employeeDocumentRepository;

    @Autowired
    EmployeeHistoryRepository employeeHistoryRepository;

    @Test
    void entityTest() {
        var jobTitles = jobTitleRepository
                .saveAll(
                    List.of(
                        new JobTitle(null, "CEO", 1000.0, 4000.0),
                        new JobTitle(null, "Manager", 800.0, 3000.0),
                        new JobTitle(null, "Engineer", 600.0, 2000.0)
                    )
                );
        assertNotNull(jobTitles);
        assertThat(jobTitles).asList().hasSize(3);
        assertThat(jobTitleRepository.findAll())
                .asList()
                .hasSize(3)
                .containsAll(jobTitles);

        var departments = departmentRepository
                .saveAll(
                    List.of(
                            new Department(null, "BUSINESS"),
                            new Department(null, "TECHNOLOGY"),
                            new Department(null, "HR")
                    )
                );

        assertNotNull(departments);
        assertThat(departments)
                .asList()
                .hasSize(3);
        assertThat(departmentRepository.findAll())
                .asList()
                .hasSize(3)
                .containsAll(departments);

        var emp1 = employeeRepository
                .save(
                    new Employee(
                            null,
                            "John",
                            "Doe",
                            "prateek.m@all-in-one.net",
                            "+4512378456",
                            LocalDate.now(),
                            jobTitles.get(1).jobId(),
                            200.00,
                            null,
                            departments.get(1).departmentId(),
                            new EmployeeHistory(null, LocalDate.now(), null, jobTitles.get(1).jobId(), departments.get(1).departmentId(), null),
                            Set.of(
                                    new EmployeeDocument(null, "IT-Return", "".getBytes(), null)
                            ),
                            null
                    )
                );
        System.out.println(emp1);
        System.out.println(employeeHistoryRepository.findAll());
        System.out.println(employeeDocumentRepository.findAll());

        assertNotNull(emp1.employeeId());
        assertThat(emp1).isNotNull();
        assertThat(employeeRepository.findAll())
                .asList()
                .hasSize(1);
    }
}
