package net.samitkumar.hrmanagement.routers;

import net.samitkumar.hrmanagement.TestcontainersConfiguration;
import net.samitkumar.hrmanagement.repositories.DepartmentRepository;
import net.samitkumar.hrmanagement.repositories.JobTitleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@AutoConfigureWebTestClient
@WithMockUser
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
