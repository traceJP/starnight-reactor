package com.tracejp.starnight.reactor.controller.admin;

import com.tracejp.starnight.reactor.controller.BaseController;
import com.tracejp.starnight.reactor.exception.ServiceException;
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
                .GET("/admin/user/page", this::listStudent).build();
    }

    public Mono<ServerResponse> listStudent(ServerRequest request) {
        var pageSize = request.queryParams().get("pageSize").get(0);
        var pageNum = request.queryParams().get("pageNum").get(0);
        return userService.findPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize))
                .flatMap(super::success);
//        return Mono.defer(() -> Mono.error(new ServiceException("测试异常")));
    }

}
