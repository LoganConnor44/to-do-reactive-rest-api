package com.loganconnor44.todoreactiverestapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loganconnor44.todoreactiverestapi.events.TaskCreatedEvent;
import com.loganconnor44.todoreactiverestapi.publishers.TaskCreatedPublisher;
import com.loganconnor44.todoreactiverestapi.models.Task;
import com.loganconnor44.todoreactiverestapi.services.TaskService;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import java.net.URI;

@RestController
@RequestMapping(value = "/to-do")
public class TaskController {

    private final ObjectMapper objectMapper;
    private final Flux<TaskCreatedEvent> events;
    private final MediaType mediaType = MediaType.APPLICATION_JSON;
    private final TaskService taskService;

    public TaskController(TaskCreatedPublisher taskCreatedPublisher,
                          ObjectMapper objectMapper,
                          TaskService taskService) {
        this.taskService = taskService;
        this.events = Flux.create(taskCreatedPublisher).share();
        this.objectMapper = objectMapper;
    }

    @GetMapping(value = "/task-events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Publisher<String> getAllEvents() {
        return this.events.map(taskThatTriggeredEvent -> {
            try {
                return objectMapper.writeValueAsString(taskThatTriggeredEvent);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @GetMapping(value = "/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Publisher<Task> getById(@PathVariable("id") String id) {
        return this.taskService.getById(id);
    }

    @PostMapping(value = "/task")
    public Publisher<ResponseEntity<Task>> create(@RequestBody Task task) {
        return this.taskService.create(task)
                .map( x -> ResponseEntity.created(URI.create("/to-do/task/" + x.getId()))
                        .contentType(mediaType)
                        .build());
    }
}