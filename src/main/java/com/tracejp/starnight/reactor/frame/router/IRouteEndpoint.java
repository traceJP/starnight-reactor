package com.tracejp.starnight.reactor.frame.router;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/14 14:35
 */
public interface IRouteEndpoint {

    RouterFunction<ServerResponse> endpoint();

}
