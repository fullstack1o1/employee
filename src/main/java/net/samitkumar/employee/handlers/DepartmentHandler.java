package net.samitkumar.employee.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.samitkumar.employee.models.Department;
import net.samitkumar.employee.repositories.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class DepartmentHandler {
    final DepartmentRepository departmentRepository;
    public Mono<ServerResponse> allDepartment(ServerRequest request) {
        return ServerResponse
                .ok()
                .bodyValue(departmentRepository.findAll());
    }

    public Mono<ServerResponse> departmentById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return Mono.fromCallable(() -> departmentRepository.findById(id).orElseThrow())
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> newDepartment(ServerRequest request) {
        return request
                .bodyToMono(Department.class)
                .map(departmentRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateDepartment(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return request.bodyToMono(Department.class)
                .zipWith(Mono.fromCallable(() -> departmentRepository.findById(id).orElseThrow()), (newDepartment, existingDepartment) -> {
                    return new Department(existingDepartment.departmentId(), newDepartment.departmentName());
                })
                .map(departmentRepository::save)
                .flatMap(updatedDepartment -> ServerResponse.ok().bodyValue(updatedDepartment))
                .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue("Error updating department: " + ex.getMessage()));
    }


    public Mono<ServerResponse> deleteDepartment(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return Mono.fromRunnable(() -> departmentRepository.deleteById(id))
                .then(ServerResponse.ok().build());
    }
}
