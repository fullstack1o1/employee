package net.samitkumar.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration(proxyBeanMethods = false)
@Import(TestcontainersConfiguration.class)
public class EmployeeManagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(EmployeeApplication::main).with(EmployeeManagementApplication.class).run(args);
	}

}
