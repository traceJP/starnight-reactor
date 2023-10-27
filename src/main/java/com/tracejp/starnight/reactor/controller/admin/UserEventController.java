package com.tracejp.starnight.reactor.controller.admin;

import com.tracejp.starnight.reactor.controller.BaseController;
import com.tracejp.starnight.reactor.entity.param.query.UserEventLogQuery;
import com.tracejp.starnight.reactor.service.IUserEventLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/10/27 16:24
 */
@RequiredArgsConstructor
@RestController
public class UserEventController extends BaseController {

    private final IUserEventLogService userEventLogService;

    @Override
    public RouterFunction<ServerResponse> endpoint() {
        return RouterFunctions.route()
                .GET("/admin/usereventlog/list", this::list)  // 列表
                .build();
    }

    private Mono<ServerResponse> list(ServerRequest request) {
        UserEventLogQuery query = new UserEventLogQuery(request.queryParams());
        return userEventLogService.listPage(query)
                .flatMap(super::success);
    }

}
