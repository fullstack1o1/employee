package net.samitkumar.hrmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration(proxyBeanMethods = false)
@Import(TestcontainersConfiguration.class)
public class TestHrManagementApplication {

	public static void main(String[] args) {
		SpringApplication.from(HrManagementApplication::main).with(TestHrManagementApplication.class).run(args);
	}

}
