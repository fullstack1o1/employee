package net.samitkumar.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class EmployeeTestApplication {

	public static void main(String[] args) {
		SpringApplication.from(EmployeeApplication::main)
				.with(TestcontainersConfiguration.class)
				.with(EmployeeTestApplication.class)
				.run(args);
	}

}
