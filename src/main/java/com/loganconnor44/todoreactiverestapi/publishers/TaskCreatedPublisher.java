package com.loganconnor44.todoreactiverestapi.publishers;

import com.loganconnor44.todoreactiverestapi.events.TaskCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class TaskCreatedPublisher implements ApplicationListener<TaskCreatedEvent>, Consumer<FluxSink<TaskCreatedEvent>>{

    private final Executor executor;
    private final BlockingQueue<TaskCreatedEvent> queue =  new LinkedBlockingQueue<>();

    public TaskCreatedPublisher(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void onApplicationEvent(TaskCreatedEvent event) {
        this.queue.offer(event);
    }

    @Override
    public void accept(FluxSink<TaskCreatedEvent> sink) throws RuntimeException {
        this.executor.execute(() -> {
            while (true)
                try {
                    TaskCreatedEvent event = queue.take();
                    sink.next(event);
                }
                catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
            }
        );
    }
}