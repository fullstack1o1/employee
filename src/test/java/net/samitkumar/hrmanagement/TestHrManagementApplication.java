package net.samitkumar.hrmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestHrManagementApplication {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"));
				//.withInitScript("db/migration/V1__schema.sql");
	}

	public static void main(String[] args) {
		SpringApplication.from(AllInOneApplication::main).with(TestHrManagementApplication.class).run(args);
	}

}
