package com.loganconnor44.todoreactiverestapi.services;

import com.loganconnor44.todoreactiverestapi.events.TaskCreatedEvent;
import com.loganconnor44.todoreactiverestapi.models.Task;
import com.loganconnor44.todoreactiverestapi.repositories.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ApplicationEventPublisher publisher;

    TaskService(ApplicationEventPublisher publisher, TaskRepository taskRepository) {
        this.publisher = publisher;
        this.taskRepository = taskRepository;
    }

    public Flux<Task> getAll() {
        return this.taskRepository.findAll();
    }

    public Mono<Task> getById(String id) {
        return this.taskRepository.findById(id);
    }

    public Mono<Task> create(Task task) {
        return this.taskRepository
                .save(task)
                .doOnSuccess(newTask -> this.publisher.publishEvent(new TaskCreatedEvent(newTask)));
    }
}
