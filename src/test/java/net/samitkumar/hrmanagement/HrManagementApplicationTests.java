package net.samitkumar.hrmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
@Import(TestcontainersConfiguration.class)
class HrManagementApplicationTests {

	@Test
	void contextLoads() {
	}

}
