package net.samitkumar.hrmanagement.handlers;

import lombok.RequiredArgsConstructor;
import net.samitkumar.hrmanagement.models.EmployeeHistory;
import net.samitkumar.hrmanagement.repositories.EmployeeHistoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmployeeHistoryHandler {
    final EmployeeHistoryRepository employeeHistoryRepository;

    public Mono<ServerResponse> allEmployeeHistory(ServerRequest request) {
        return ServerResponse
                .ok()
                .bodyValue(employeeHistoryRepository.findAll());
    }

    public Mono<ServerResponse> employeeHistoryById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return Mono.fromCallable(() -> employeeHistoryRepository.findById(id).orElseThrow())
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> newEmployeeHistory(ServerRequest request) {
        return request
                .bodyToMono(EmployeeHistory.class)
                .map(employeeHistoryRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateEmployeeHistory(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return request
                .bodyToMono(EmployeeHistory.class)
                .zipWith(Mono.fromCallable(() -> employeeHistoryRepository.findById(id).orElseThrow()), (newEmpHistory, existingEmpHistory) -> {
                    return
                            new EmployeeHistory(existingEmpHistory.historyId(), newEmpHistory.startDate(), newEmpHistory.endDate(), newEmpHistory.jobId(), newEmpHistory.departmentId(), existingEmpHistory.employeeId());
                })
                .map(employeeHistoryRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteEmployeeHistory(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return Mono.fromRunnable(() -> employeeHistoryRepository.deleteById(id))
                .then(ServerResponse.ok().build());
    }
}
