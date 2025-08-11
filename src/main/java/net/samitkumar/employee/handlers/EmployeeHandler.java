package net.samitkumar.employee.handlers;

import lombok.RequiredArgsConstructor;
import net.samitkumar.employee.models.Employee;
import net.samitkumar.employee.models.EmployeeDocument;
import net.samitkumar.employee.models.EmployeeHistory;
import net.samitkumar.employee.models.EmployeeRequest;
import net.samitkumar.employee.repositories.EmployeeRepository;
import net.samitkumar.employee.utilities.EmployeeUtility;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class EmployeeHandler {
    final EmployeeRepository employeeRepository;
    public Mono<ServerResponse> allEmployee(ServerRequest request) {
        return ServerResponse
                .ok()
                .bodyValue(employeeRepository.findAll());
    }

    public Mono<ServerResponse> employeeById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return Mono.fromCallable(() -> employeeRepository.findById(id).orElseThrow())
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> newEmployee(ServerRequest request) {
        return request
                .bodyToMono(EmployeeRequest.class)
                .map(employee -> new Employee(
                        null,
                        null,
                        employee.firstName(),
                        employee.lastName(),
                        employee.email(),
                        employee.phoneNumber(),
                        employee.hireDate(),
                        employee.jobId(),
                        employee.salary(),
                        null,
                        employee.departmentId(),
                        new EmployeeHistory(null, employee.startDate(), LocalDate.of(9999, 1, 1), employee.jobId(), employee.departmentId(), null),
                        Set.of(),
                        null))
                .map(employeeRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateEmployee(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return request
                .bodyToMono(Employee.class)
                .zipWith(Mono.fromCallable(() -> employeeRepository.findById(id).orElseThrow()), (newEmp, existingEmp) -> {
                    return new Employee(
                            existingEmp.employeeId(),
                            existingEmp.empNo(),
                            newEmp.firstName(),
                            newEmp.lastName(),
                            newEmp.email(),
                            newEmp.phoneNumber(),
                            newEmp.hireDate(),
                            newEmp.jobId(),
                            newEmp.salary(),
                            newEmp.managerId(),
                            newEmp.departmentId(),
                            nonNull(newEmp.employeeHistory())? newEmp.employeeHistory() : existingEmp.employeeHistory(),
                            nonNull(newEmp.employeeDocuments()) ? newEmp.employeeDocuments() : existingEmp.employeeDocuments(),
                            null
                            );
                })
                .map(employeeRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteEmployee(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return Mono.fromRunnable(() -> employeeRepository.deleteById(id))
                .then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> multipartNewEmployee(ServerRequest request) {
        return request
                .multipartData()
                .flatMap(stringPartMultiValueMap -> {
                    //var id = formFieldPartValue(stringPartMultiValueMap, "id");
                    var firstName = formFieldPartValue(stringPartMultiValueMap, "firstName");
                    var lastName = formFieldPartValue(stringPartMultiValueMap, "lastName");
                    var email = formFieldPartValue(stringPartMultiValueMap, "email");
                    var phoneNumber = formFieldPartValue(stringPartMultiValueMap, "phoneNumber");
                    var hireDate = formFieldPartValue(stringPartMultiValueMap, "hireDate");
                    var jobId = formFieldPartValue(stringPartMultiValueMap, "jobId");
                    var salary = formFieldPartValue(stringPartMultiValueMap, "salary");
                    var managerId = formFieldPartValue(stringPartMultiValueMap, "managerId");
                    AggregateReference<Employee, Integer> managerIdAggregateReference = (nonNull(managerId) && StringUtils.hasText(managerId) && !"undefined".equals(managerId)) ? AggregateReference.to(Integer.valueOf(managerId)) : null;
                    var departmentId = formFieldPartValue(stringPartMultiValueMap, "departmentId");
                    var startDate = formFieldPartValue(stringPartMultiValueMap, "startDate");

                    assert hireDate != null;
                    assert salary != null;
                    assert jobId != null;
                    assert departmentId != null;
                    assert startDate != null;

                    var files = Objects.requireNonNullElse(stringPartMultiValueMap.get("documents"), List.of()).stream().map(part -> (FilePart) part).toList();

                    return Flux.fromIterable(files)
                            .flatMap(filePart -> DataBufferUtils
                                    .join(filePart.content())
                                    .map(dataBuffer -> new EmployeeDocument(
                                            null,
                                            filePart.filename(),
                                            EmployeeUtility.getDocumentsType(filePart),
                                            dataBuffer.asByteBuffer().array(),
                                            null)))
                            .collectList()
                            .map(Set::copyOf)
                            .map(documents -> new Employee(
                                    null,
                                    null,
                                    firstName,
                                    lastName,
                                    email,
                                    phoneNumber,
                                    LocalDate.parse(hireDate),
                                    Integer.valueOf(jobId),
                                    Double.parseDouble(salary),
                                    managerIdAggregateReference,
                                    Integer.valueOf(departmentId),
                                    new EmployeeHistory(null, LocalDate.parse(startDate), LocalDate.of(9999, 1, 1), Integer.valueOf(jobId), Integer.valueOf(departmentId), null),
                                    documents, null));
                })
                .map(employeeRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    private String formFieldPartValue(MultiValueMap<String, Part> stringPartMultiValueMap, String fieldName) {
        return stringPartMultiValueMap.getFirst(fieldName) instanceof FormFieldPart
                ? ((FormFieldPart) stringPartMultiValueMap.getFirst(fieldName)).value()
                : null;
    }
}
