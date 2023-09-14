package com.tracejp.starnight.reactor.frame.router;

import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/14 14:41
 */
public class RouterEndpointsBuilder {

    /**
     * 路由函数列表
     */
    private final List<RouterFunction<ServerResponse>> routerFunctionsList;

    /**
     * 上下文路径
     */
    private final String contextPath;

    public RouterEndpointsBuilder(String contextPath) {
        routerFunctionsList = new ArrayList<>(16);
        this.contextPath = contextPath;
    }

    public RouterEndpointsBuilder add(IRouteEndpoint route) {
        routerFunctionsList.add(route.endpoint());
        return this;
    }

    public RouterFunction<ServerResponse> build() {
        RouterFunctions.Builder builder = RouterFunctions.route()
                .nest(RequestPredicates.path(contextPath),
                        () -> routerFunctionsList.stream()
                                .reduce(RouterFunction::and)
                                .orElse(null));
        if (routerFunctionsList.isEmpty()) {
            return request -> Mono.empty();
        }
        routerFunctionsList.clear();
        return builder.build();
    }

}
