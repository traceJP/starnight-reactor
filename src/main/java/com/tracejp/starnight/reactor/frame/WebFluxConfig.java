package com.tracejp.starnight.reactor.frame;

import com.tracejp.starnight.reactor.frame.router.IRouteEndpoint;
import com.tracejp.starnight.reactor.frame.router.RouterEndpointsBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * <p> WebFlux Config <p/>
 *
 * @author traceJP
 * @since 2023/9/14 14:33
 */
@Configuration
public class WebFluxConfig {

    @Value("${server.context-path}")
    private final String contextPath = "/api";

    @Bean
    RouterFunction<ServerResponse> customEndpoints(ApplicationContext context) {
        var builder = new RouterEndpointsBuilder(contextPath);
        context.getBeansOfType(IRouteEndpoint.class).values().forEach(builder::add);
        return builder.build();
    }

}
