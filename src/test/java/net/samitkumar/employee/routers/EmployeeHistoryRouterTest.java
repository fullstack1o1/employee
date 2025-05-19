package net.samitkumar.employee.routers;

import net.samitkumar.employee.TestcontainersConfiguration;
import net.samitkumar.employee.repositories.DepartmentRepository;
import net.samitkumar.employee.repositories.JobTitleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@AutoConfigureWebTestClient
@Import(TestcontainersConfiguration.class)
public class EmployeeHistoryRouterTest {


    @Autowired
    WebTestClient webTestClient;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    JobTitleRepository jobTitleRepository;

    @Test
    void employeeHistoryTest() {
        assertAll(
                () -> webTestClient
                        .get()
                        .uri("/api/employee/history")
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody()
                        .json("""
                            []
                        """)
        );
    }
}
