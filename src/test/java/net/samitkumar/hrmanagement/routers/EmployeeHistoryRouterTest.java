package net.samitkumar.hrmanagement.routers;

import net.samitkumar.hrmanagement.models.Department;
import net.samitkumar.hrmanagement.models.JobTitle;
import net.samitkumar.hrmanagement.repositories.DepartmentRepository;
import net.samitkumar.hrmanagement.repositories.JobTitleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(properties = "spring.flyway.enabled=false")
@Testcontainers
@AutoConfigureWebTestClient
@WithMockUser
public class EmployeeHistoryRouterTest {

    @Container
    @ServiceConnection
    final static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"))
            .withInitScript("db/migration/V1__schema.sql");


    @Autowired
    WebTestClient webTestClient;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    JobTitleRepository jobTitleRepository;

    @Test
    void employeeHistoryTest() {
        var dept = departmentRepository
                .save(
                        new Department(null, "IT")
                );

        var job = jobTitleRepository
                .save(
                        new JobTitle(null,"Engineer", 2000.0, 4000.00)
                );

        assertAll(
                () -> webTestClient
                        .get()
                        .uri("/db/employee/history")
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
