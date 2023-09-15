package com.tracejp.starnight.reactor.controller.global;

import com.tracejp.starnight.reactor.controller.BaseController;
import com.tracejp.starnight.reactor.entity.base.LoginUser;
import com.tracejp.starnight.reactor.entity.param.LoginParam;
import com.tracejp.starnight.reactor.handler.token.TokenHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
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
 * @since 2023/9/15 10:53
 */
@RequiredArgsConstructor
@RestController
public class LoginController extends BaseController {

    private final TokenHandler tokenHandler;

    private final ReactiveAuthenticationManager authorizationManager;

    @Override
    public RouterFunction<ServerResponse> endpoint() {
        return RouterFunctions.route()
                .POST("/user/login", this::login)
                .build();
    }

    /**
     * 用户登录
     */
    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginParam.class)
                .flatMap(loginParam -> authorizationManager.authenticate(loginParam.getAuthenticationToken()))
                .map(Authentication::getDetails)
                .cast(LoginUser.class)
                // TODO 记录日志
                .flatMap(tokenHandler::createToken)
                .flatMap(super::success);
    }

}
