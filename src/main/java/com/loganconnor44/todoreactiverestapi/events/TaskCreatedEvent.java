package com.loganconnor44.todoreactiverestapi.events;

import com.loganconnor44.todoreactiverestapi.models.Task;
import org.springframework.context.ApplicationEvent;

public class TaskCreatedEvent extends ApplicationEvent {
    public TaskCreatedEvent(Task task) {
        super(task);
    }
}
