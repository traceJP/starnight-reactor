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
                .GET("/admin/user/page", this::pageUser)
                .build();
    }


    public Mono<ServerResponse> pageUser(ServerRequest request) {
        UserQuery userQuery = new UserQuery(request.queryParams());
        return userService.findPage(userQuery).flatMap(super::success);
    }

}
