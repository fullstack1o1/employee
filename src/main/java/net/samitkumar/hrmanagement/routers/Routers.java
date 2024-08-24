package net.samitkumar.hrmanagement.routers;

import lombok.extern.slf4j.Slf4j;
import net.samitkumar.hrmanagement.handlers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;


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
                                .POST("", employeeHandler::newEmployee)
                                .POST("/multipart", employeeHandler::multipartNewEmployee)
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
        return ReactiveSecurityContextHolder
                .getContext()
                .map(SecurityContext::getAuthentication)
                .map(authentication -> {
                    var roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
                    var name = authentication.getName();
                    return Map.of("roles", roles, "name", name);
                })
                .flatMap(ServerResponse.ok()::bodyValue);
    }
}
