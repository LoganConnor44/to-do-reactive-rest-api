package com.loganconnor44.todoreactiverestapi.configs;

import com.loganconnor44.todoreactiverestapi.models.Task;
import com.loganconnor44.todoreactiverestapi.repositories.TaskRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
//
//@Log4j2
//@Component
//public class Application implements ApplicationListener<ApplicationReadyEvent> {
//    private final TaskRepository taskRepository;
//
//    public Application(TaskRepository taskRepository) {
//        this.taskRepository = taskRepository;
//    }
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//        this.taskRepository.findAll().subscribe(task -> System.out.println(task.toString()));
//    }
//}
