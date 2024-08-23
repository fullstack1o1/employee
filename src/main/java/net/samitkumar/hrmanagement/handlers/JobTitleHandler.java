package net.samitkumar.hrmanagement.handlers;

import lombok.RequiredArgsConstructor;
import net.samitkumar.hrmanagement.models.JobTitle;
import net.samitkumar.hrmanagement.repositories.JobTitleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JobTitleHandler {
    final JobTitleRepository jobTitleRepository;

    public Mono<ServerResponse> allJobTitle(ServerRequest request) {
        return ServerResponse
                .ok().bodyValue(jobTitleRepository.findAll());
    }

    public Mono<ServerResponse> jobTitleById(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return Mono.fromCallable(() -> jobTitleRepository.findById(id).orElseThrow())
                .flatMap(ServerResponse.ok()::bodyValue)
                .onErrorResume(e -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(e.getMessage()));
    }

    public Mono<ServerResponse> newJobTitle(ServerRequest request) {
        return request
                .bodyToMono(JobTitle.class)
                .map(jobTitleRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateJobTitle(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return request
                .bodyToMono(JobTitle.class)
                .zipWith(Mono.fromCallable(() -> jobTitleRepository.findById(id).orElseThrow()), (newjobTitle, existingJobTitle) -> {
                    return new JobTitle(existingJobTitle.jobId(), newjobTitle.title(), newjobTitle.minSalary(), newjobTitle.maxSalary());
                })
                .map(jobTitleRepository::save)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteJobTitle(ServerRequest request) {
        var id = Integer.parseInt(request.pathVariable("id"));
        return Mono.fromRunnable(() -> jobTitleRepository.deleteById(id))
                .then(ServerResponse.ok().build());
    }
}
