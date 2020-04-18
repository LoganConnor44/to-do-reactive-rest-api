package com.loganconnor44.todoreactiverestapi.repositories;

import com.loganconnor44.todoreactiverestapi.models.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
    Flux<Task> findByOwner(String owner);
}
