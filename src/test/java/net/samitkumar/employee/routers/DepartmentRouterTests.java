package net.samitkumar.employee.routers;

import net.samitkumar.employee.TestcontainersConfiguration;
import net.samitkumar.employee.repositories.DepartmentRepository;
import net.samitkumar.employee.repositories.EmployeeDocumentRepository;
import net.samitkumar.employee.repositories.EmployeeHistoryRepository;
import net.samitkumar.employee.repositories.JobTitleRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TestcontainersConfiguration.class)
class DepartmentRouterTests {

	@Autowired
	WebTestClient webTestClient;

	//This object are not needed but we want to validate some data in the flow
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	JobTitleRepository jobTitleRepository;
	@Autowired
	EmployeeHistoryRepository employeeHistoryRepository;
	@Autowired
	EmployeeDocumentRepository employeeDocumentRepository;

	@Test
	@Order(1)
	void departmentRouterTest() {
		assertAll(
				() -> {
					webTestClient
							.get()
							.uri("/api/department")
							.exchange()
							.expectStatus()
							.isOk()
							.expectBody()
							.json("""
								[]
							""");

				},
				() -> {
					webTestClient
							.post()
							.uri("/api/department")
							.contentType(MediaType.APPLICATION_JSON)
							.bodyValue("""
							{
								"departmentName": "IT"
							}
						""")
							.exchange()
							.expectStatus()
							.isOk()
							.expectBody()
							.json("""
							{
								"departmentId": 1,
								"departmentName": "IT"
							}
						""");
				},
				() -> {
					webTestClient
							.get()
							.uri("/api/department/{id}", 1)
							.exchange()
							.expectStatus()
							.isOk()
							.expectBody()
							.json("""
       							 {
									  "departmentId": 1,
									  "departmentName": "IT"
								  }
							""");
				},
				() -> {
					webTestClient
							.put()
							.uri("/api/department/{id}", 1)
							.contentType(MediaType.APPLICATION_JSON)
							.bodyValue("""
							{
								"departmentName": "TECHNOLOGY"
							}
							""")
							.exchange()
							.expectStatus()
							.isOk()
							.expectBody()
							.json("""
								{
									"departmentId": 1,
									"departmentName": "TECHNOLOGY"
							  	}
							""");

				},
				() -> {
					webTestClient
							.get()
							.uri("/api/department")
							.exchange()
							.expectBody()
							.json("""
								[
									{
										"departmentId": 1,
										"departmentName": "TECHNOLOGY"
									}
								]
							""");
				},
				() -> {
					webTestClient
							.delete()
							.uri("/api/department/{id}",1)
							.exchange()
							.expectStatus()
							.isOk();
				},
				() -> webTestClient
						.get()
						.uri("/api/department/{id}", 1)
						.exchange()
						.expectStatus()
						.is5xxServerError()
		);
	}

}
