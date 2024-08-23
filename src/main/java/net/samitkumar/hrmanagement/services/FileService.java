package net.samitkumar.hrmanagement.services;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;


public interface FileService {
    Mono<Void> upload(FilePart filePart);
    Mono<ByteBuffer> download(String fileName);
    Mono<ByteBuffer> view(String fileName);
    Flux<String> listAllFile();
}
