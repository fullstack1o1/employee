package net.samitkumar.hrmanagement.handlers;

import lombok.RequiredArgsConstructor;
import net.samitkumar.hrmanagement.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.NoSuchFileException;
import java.util.Map;

import static net.samitkumar.hrmanagement.utilities.AllInOneUtility.mediaTypeByFileExtension;

@Component
@RequiredArgsConstructor
public class FileHandler {
    final FileService fileService;
    public Mono<ServerResponse> allFiles(ServerRequest request) {
        return fileService.listAllFile()
                .collectList() // Collect files into a list
                .flatMap(files -> ServerResponse.ok().bodyValue(files))
                .onErrorMap(ex -> new RuntimeException("Error fetching files: " + ex.getMessage()));
    }

    public Mono<ServerResponse> scan(ServerRequest request) {
        byte[] data = new byte[1024 * 1024 * 1024]; // Allocate 1GB of memory
        return ServerResponse.ok().bodyValue(Map.of("message", "SUCCESS"));
    }

    public Mono<ServerResponse> viewFile(ServerRequest request) {
        var fileName = request.pathVariable("id");
        var mimeType = mediaTypeByFileExtension(fileName);
        return fileService
                .view(fileName)
                .flatMap(ServerResponse
                        .ok()
                        .contentType(mediaTypeByFileExtension(fileName))::bodyValue)
                .onErrorResume(NoSuchFileException.class, ex -> ServerResponse
                        .status(HttpStatus.NOT_FOUND)
                        .bodyValue(Map.of("message", "File not found: " + ex.getMessage()))
                )
                .onErrorResume(ex -> ServerResponse
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(Map.of("message","Internal Server Error: " + ex.getMessage()))
                );
    }

    public Mono<ServerResponse> fileDownload(ServerRequest request) {
        var fileName = request.pathVariable("id");
        return fileService
                .download(fileName)
                .flatMap(ServerResponse
                        .ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=%s".formatted(fileName))
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        ::bodyValue)
                .onErrorResume(NoSuchFileException.class, ex -> ServerResponse
                        .status(HttpStatus.NOT_FOUND)
                        .bodyValue(Map.of("message", "File not found: " + ex.getMessage()))
                )
                .onErrorResume(ex -> ServerResponse
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(Map.of("message","Internal Server Error: " + ex.getMessage()))
                );
    }

    public Mono<ServerResponse> fileUpload(ServerRequest request) {
        return request
                .multipartData()
                .map(stringPartMultiValueMap -> stringPartMultiValueMap.get("file"))
                .flatMapMany(Flux::fromIterable)
                .cast(FilePart.class)
                .flatMap(fileService::upload)
                .then(ServerResponse.ok().bodyValue(Map.of("message", "SUCCESS")))
                .onErrorResume(ex -> ServerResponse
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(ex.getMessage())
                );
    }
}
