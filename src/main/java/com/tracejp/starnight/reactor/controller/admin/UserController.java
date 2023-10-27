package com.tracejp.starnight.reactor.controller.admin;

import com.tracejp.starnight.reactor.controller.BaseController;
import com.tracejp.starnight.reactor.entity.param.UserQuery;
import com.tracejp.starnight.reactor.service.IUserService;
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
 * @since 2023/9/13 9:08
 */
@RequiredArgsConstructor
@RestController("adminUserController")
public class UserController extends BaseController {

    private final IUserService userService;

    @Override
    public RouterFunction<ServerResponse> endpoint() {
        return RouterFunctions.route()
                // 列表
                .GET("/admin/user/list", this::list)
                .build();
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        UserQuery userQuery = new UserQuery(request.queryParams());
        return userService.listPage(userQuery).flatMap(super::success);
    }

}
