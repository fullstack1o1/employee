package net.samitkumar.hrmanagement.routers;

import net.samitkumar.hrmanagement.TestcontainersConfiguration;
import net.samitkumar.hrmanagement.repositories.DepartmentRepository;
import net.samitkumar.hrmanagement.repositories.EmployeeDocumentRepository;
import net.samitkumar.hrmanagement.repositories.EmployeeHistoryRepository;
import net.samitkumar.hrmanagement.repositories.JobTitleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@AutoConfigureWebTestClient
@WithMockUser
@Import(TestcontainersConfiguration.class)
class JobTitleRouterTests {
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
	void jobTitleRouterTest() {
		assertAll(
				//all
				() -> webTestClient
						.get()
						.uri("/db/jobtitle")
						.exchange()
						.expectStatus()
						.isOk()
						.expectBody()
						.json("""
      						[]
						"""),
				//save
				() -> webTestClient
						.post()
						.uri("/db/jobtitle")
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue("""
							{
								"title" : "Engineer",
								"minSalary": 4000.00,
								"maxSalary": 6000.00
							}
						""")
						.exchange()
						.expectStatus()
						.isOk()
						.expectBody()
						.json("""
							{
								"jobId": 1,
								"title" : "Engineer",
								"minSalary": 4000.00,
								"maxSalary": 6000.00
							}
						"""),
				//find by Id
				() -> webTestClient
						.get()
						.uri("/db/jobtitle/{id}", 1)
						.exchange()
						.expectStatus()
						.isOk()
						.expectBody()
						.json("""
							{
								"jobId": 1,
								"title" : "Engineer",
								"minSalary": 4000.00,
								"maxSalary": 6000.00
							}
						"""),
				//Update
				() -> webTestClient
						.put()
						.uri("/db/jobtitle/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue("""
							{
								"title" : "Developer",
								"minSalary": 4000.00,
								"maxSalary": 6000.00
							}
						""")
						.exchange()
						.expectStatus()
						.isOk()
						.expectBody()
						.json("""
							{
								"jobId": 1,
								"title" : "Developer",
								"minSalary": 4000.00,
								"maxSalary": 6000.00
							}
						"""),
				//delete
				() -> webTestClient
						.delete()
						.uri("/db/jobtitle/{id}", 1)
						.exchange()
						.expectStatus()
						.isOk(),
				//Find by id and check If we have no data
				() -> webTestClient
						.get()
						.uri("/db/jobtitle/{id}", 1)
						.exchange()
						.expectStatus()
						.is5xxServerError()

		);
	}

}
