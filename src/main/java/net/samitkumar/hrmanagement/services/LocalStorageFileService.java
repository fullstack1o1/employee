package net.samitkumar.hrmanagement.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.BaseStream;

@Service
@Slf4j
public class LocalStorageFileService implements FileService {

    @Value("${spring.application.album.storage.local.path}")
    private String basePath;
    @Value("${spring.application.album.thumbnail}")
    private boolean thumbnail;

    @Override
    public Mono<Void> upload(FilePart filePart) {
        return filePart
                .transferTo(Path.of(basePath).resolve(filePart.filename()))
                //handle non jpg, jpeg and png format here, or else it would be an exception
                .then(Mono.fromRunnable(() -> {
                    if(thumbnail) {
                        try {
                            // Get the file path and name
                            String filePath = Path.of(basePath).resolve(filePart.filename()).toString();
                            String thumbnailPath = Path.of(basePath).resolve("thumbnail_" + filePart.filename()).toString();
                            // Generate the thumbnail path and name
                            Thumbnails.of(new File(filePath))
                                    .size(100, 100)
                                    .toFile(new File(thumbnailPath));
                            log.info("Thumbnail created successfully: {}", thumbnailPath);
                        } catch (IOException e) {
                            log.error("Error creating thumbnail", e);
                        }
                    } else {
                        log.info("Thumbnail generator disabled");
                    }

                }))
                .then()
                .doOnError(throwable -> log.error("file upload FAIL", throwable))
                .doOnSuccess(e -> log.info("{} upload SUCCESS", filePart.filename()));
    }

    @Override
    public Mono<ByteBuffer> download(String fileName) {
        return Mono
                .fromCallable(() -> Files.readAllBytes(Path.of(basePath).resolve(fileName)))
                .onErrorResume(Mono::error)
                .flatMap(bytes -> Mono.just(ByteBuffer.wrap(bytes)))
                .doOnError(e -> log.error("{} file download or view FAIL", fileName, e))
                .doOnSuccess(r -> log.info("{} file download or view SUCCESS", fileName));
    }

    @Override
    public Mono<ByteBuffer> view(String fileName) {
        return download(fileName);
    }

    @Override
    @SneakyThrows
    public Flux<String> listAllFile() {
        return Flux.using(
                () -> Files.walk(Path.of(basePath)),
                (stream) -> Flux
                        .fromStream(
                                stream
                                        .filter(Files::isRegularFile)
                                        .map(Path::getFileName)
                                        .map(Path::toString)
                                        /*.filter(fileName -> !fileName.contains("thumbnail_"))*/
                        ), BaseStream::close)
                .onErrorResume(Flux::error);
    }
}
