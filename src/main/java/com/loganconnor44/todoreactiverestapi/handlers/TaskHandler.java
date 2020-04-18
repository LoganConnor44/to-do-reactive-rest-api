package com.loganconnor44.todoreactiverestapi.handlers;

import com.loganconnor44.todoreactiverestapi.models.Task;
import com.loganconnor44.todoreactiverestapi.services.TaskService;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.net.URI;

@Component
public class TaskHandler {

    private final TaskService taskService;

    TaskHandler(TaskService taskService) {
        this.taskService = taskService;
    }

    public Mono<ServerResponse> getAllEvents(ServerRequest request) {
        return defaultReadResponse(this.taskService.getAll());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Flux<Task> flux = request.bodyToFlux(Task.class)
                .flatMap(this.taskService::create);
        return defaultWriteResponse(flux);
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        return defaultReadResponse(this.taskService.getById(
                this.getRequestVariableId(request)
        ));
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<Task> task) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(task, Task.class);
    }

    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Task> task) {
        return Mono
                .from(task)
                .flatMap(t -> ServerResponse
                        .created(URI.create("/to-do/task/" + t.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build());
    }

    private String getRequestVariableId(ServerRequest request) {
        return request.pathVariable("id");
    }
}
