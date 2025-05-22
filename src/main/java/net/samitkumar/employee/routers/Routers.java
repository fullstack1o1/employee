package net.samitkumar.employee.routers;

import lombok.extern.slf4j.Slf4j;
import net.samitkumar.employee.handlers.DepartmentHandler;
import net.samitkumar.employee.handlers.EmployeeDocumentsHandler;
import net.samitkumar.employee.handlers.EmployeeHandler;
import net.samitkumar.employee.handlers.EmployeeHistoryHandler;
import net.samitkumar.employee.handlers.JobTitleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;


@Configuration
@Slf4j
public class Routers {
    @Bean
    RouterFunction<ServerResponse> routerFunction(JobTitleHandler jobTitleHandler,
                                                  DepartmentHandler departmentHandler,
                                                  EmployeeHandler employeeHandler,
                                                  EmployeeDocumentsHandler employeeDocumentsHandler,
                                                  EmployeeHistoryHandler employeeHistoryHandler) {
        return RouterFunctions
                .route()
                .GET("/me", this::me)
                .path("/api", builder -> builder
                        .path("/department", deptBuilder -> deptBuilder
                                .GET("", departmentHandler::allDepartment)
                                .GET("/{id}", departmentHandler::departmentById)
                                .POST("", departmentHandler::newDepartment)
                                .PUT("/{id}", departmentHandler::updateDepartment)
                                .DELETE("/{id}", departmentHandler::deleteDepartment))
                        .path("/jobtitle", jtbuilder -> jtbuilder
                                .GET("", jobTitleHandler::allJobTitle)
                                .GET("/{id}", jobTitleHandler::jobTitleById)
                                .POST("", jobTitleHandler::newJobTitle)
                                .PUT("/{id}", jobTitleHandler::updateJobTitle)
                                .DELETE("/{id}", jobTitleHandler::deleteJobTitle))
                        .path("/employee", empBuilder -> empBuilder
                                .path("/documents", documentbuilder -> documentbuilder
                                        .GET("", employeeDocumentsHandler::allEmployeeDocuments)
                                        .GET("/{id}", employeeDocumentsHandler::employeeDocumentsbyId)
                                        .POST("", employeeDocumentsHandler::newEmployeeDocument)
                                        .PUT("/{id}", employeeDocumentsHandler::updateEmployeeDocument)
                                        .DELETE("/{id}", employeeDocumentsHandler::deleteEmployeeDocument)
                                )
                                .path("/history", historyBuilder -> historyBuilder
                                        .GET("", employeeHistoryHandler::allEmployeeHistory)
                                        .GET("/{id}", employeeHistoryHandler::employeeHistoryById)
                                        .POST("", employeeHistoryHandler::newEmployeeHistory)
                                        .PUT("/{id}", employeeHistoryHandler::updateEmployeeHistory)
                                        .DELETE("/{id}", employeeHistoryHandler::deleteEmployeeHistory)
                                )
                                .GET("", employeeHandler::allEmployee)
                                .GET("/{id}", employeeHandler::employeeById)
                                .POST("", accept(MediaType.APPLICATION_JSON), employeeHandler::newEmployee)
                                .POST("", accept(MediaType.MULTIPART_FORM_DATA), employeeHandler::multipartNewEmployee)
                                .POST("/multipart", accept(MediaType.MULTIPART_FORM_DATA), employeeHandler::multipartNewEmployee)
                                .PUT("/{id}", employeeHandler::updateEmployee)
                                .DELETE("/{id}", employeeHandler::deleteEmployee)

                        )
                )
                .after((request, response) -> {
                    log.info("{} {} {}", request.method(), request.path(), response.statusCode());
                    return response;
                })
                .build();
    }

    private Mono<ServerResponse> me(ServerRequest request) {
        return ServerResponse.ok().bodyValue(Map.of("name", "UNKNOWN"));
    }
}
