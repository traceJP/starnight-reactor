package com.tracejp.starnight.reactor.controller.global;

import com.tracejp.starnight.reactor.controller.BaseController;
import com.tracejp.starnight.reactor.entity.base.LoginUser;
import com.tracejp.starnight.reactor.entity.enums.RoleEnum;
import com.tracejp.starnight.reactor.entity.enums.UserStatusEnum;
import com.tracejp.starnight.reactor.entity.param.LoginParam;
import com.tracejp.starnight.reactor.entity.param.RegisterParam;
import com.tracejp.starnight.reactor.handler.token.TokenHandler;
import com.tracejp.starnight.reactor.service.IUserEventLogService;
import com.tracejp.starnight.reactor.service.IUserService;
import com.tracejp.starnight.reactor.utils.JwtUtils;
import com.tracejp.starnight.reactor.utils.SecurityUtils;
import com.tracejp.starnight.reactor.utils.StringUtils;
import com.tracejp.starnight.reactor.utils.UUIDUtils;
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

    private final IUserEventLogService userEventLogService;

    private final IUserService userService;

    @Override
    public RouterFunction<ServerResponse> endpoint() {
        return RouterFunctions.route()
                .POST("/user/login", this::login)
                .DELETE("/user/logout", this::logout)
                .POST("/user/register/student", this::register)
                .build();
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginParam.class)
                .flatMap(loginParam -> authorizationManager.authenticate(loginParam.getAuthenticationToken()))
                .map(Authentication::getDetails)
                .cast(LoginUser.class)
                .flatMap(loginUser -> {
                    userEventLogService.saveAsync(loginUser.getUser(), "登录成功").subscribe();
                    return tokenHandler.createToken(loginUser);
                })
                .flatMap(super::success);
    }

    public Mono<ServerResponse> logout(ServerRequest request) {
        var token = SecurityUtils.getToken(request.exchange().getRequest());
        if (StringUtils.isNotEmpty(token)) {
            var username = JwtUtils.getUserName(token);
            return tokenHandler.delLoginUser(token)
                    .doOnSuccess(ignored -> logger.info("用户登出，username：{}", username))
                    .flatMap(super::success);
        }
        return success();
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(RegisterParam.class)
                .flatMap(param -> userService.getByUserName(param.userName())
                        .flatMap(user -> error("用户名已存在"))
                        .switchIfEmpty(Mono.defer(() -> {
                            var user = param.convertTo()
                                    .setPassword(SecurityUtils.encryptPassword(param.password()))
                                    .setUserUuid(UUIDUtils.randomUUID().toString())
                                    .setStatus(UserStatusEnum.Enable.getCode())
                                    .setRole(RoleEnum.STUDENT.getCode());
                            return userService.editToAll(user).then(success());
                        }))
                );
    }

}
