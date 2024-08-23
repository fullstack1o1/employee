package net.samitkumar.hrmanagement.handlers;

import lombok.RequiredArgsConstructor;
import net.samitkumar.hrmanagement.models.EmployeeDocument;
import net.samitkumar.hrmanagement.repositories.EmployeeDocumentRepository;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EmployeeDocumentsHandler {
    final EmployeeDocumentRepository employeeDocumentRepository;

    public Mono<ServerResponse> allEmployeeDocuments(ServerRequest request) {
        return Mono.fromCallable(employeeDocumentRepository::findAll)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> employeeDocumentsbyId(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return Mono.fromCallable(() -> employeeDocumentRepository.findById(id).orElseThrow())
                .flatMap(ServerResponse.ok()::bodyValue)
                .onErrorResume(e -> ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> newEmployeeDocument(ServerRequest request) {
        return request
                .multipartData()
                .map(MultiValueMap::toSingleValueMap)
                .map(stringPartMap -> stringPartMap.get("file"))
                .cast(FilePart.class)
                .flatMap(filePart -> DataBufferUtils.join(filePart.content()).map(dataBuffer -> new EmployeeDocument(null, filePart.filename(), dataBuffer.toByteBuffer().array(), 1)))
                .map(employeeDocumentRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);

    }

    public Mono<ServerResponse> updateEmployeeDocument(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> deleteEmployeeDocument(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return Mono.fromRunnable(() -> employeeDocumentRepository.deleteById(id))
                .then(ServerResponse.ok().build());
    }
}
