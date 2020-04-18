package com.loganconnor44.todoreactiverestapi.configs;

import com.loganconnor44.todoreactiverestapi.handlers.TaskHandler;
import com.loganconnor44.todoreactiverestapi.helpers.CaseInsensitiveRequestPredicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TaskEndpointConfig {

    @Bean
    RouterFunction<ServerResponse> routes(TaskHandler handler) {
        return route(i(GET("/task-events")), handler::getAllEvents)
                .andRoute(i(POST("/task/{task}")), handler::create)
                .andRoute(i(GET("/task/{id}")), handler::getById);
    }

    private static RequestPredicate i(RequestPredicate target) {
        return new CaseInsensitiveRequestPredicate(target);
    }

}
