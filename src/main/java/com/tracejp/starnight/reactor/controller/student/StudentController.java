package com.tracejp.starnight.reactor.controller.student;

import com.tracejp.starnight.reactor.controller.BaseController;
import com.tracejp.starnight.reactor.frame.router.IRouteEndpoint;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/10/18 9:44
 */
public class StudentController extends BaseController implements IRouteEndpoint {


    @Override
    public RouterFunction<ServerResponse> endpoint() {
        return RouterFunctions.route()
                .GET("/student/page", this::listStudent)
                .build();
    }

    public Mono<ServerResponse> listStudent(ServerRequest request) {
        return success("Hello World!");
    }

}
