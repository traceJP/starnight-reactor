package com.tracejp.starnight.reactor.controller.admin;

import com.tracejp.starnight.reactor.controller.BaseController;
import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.PageDomain;
import com.tracejp.starnight.reactor.entity.dto.UserDto;
import com.tracejp.starnight.reactor.entity.enums.UserStatusEnum;
import com.tracejp.starnight.reactor.entity.param.UserEditParam;
import com.tracejp.starnight.reactor.entity.param.UserQuery;
import com.tracejp.starnight.reactor.service.IUserService;
import com.tracejp.starnight.reactor.utils.SecurityUtils;
import com.tracejp.starnight.reactor.utils.StringUtils;
import com.tracejp.starnight.reactor.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

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
                .GET("/admin/user/list", this::list)  // 列表
                .GET("/admin/user/search", this::search)  // 搜索用户
                .GET("/admin/user/{id}", this::info)  // 信息
                .POST("/admin/user", this::save)  // 保存
                .PUT("/admin/user", this::update)  // 修改
                .PUT("/admin/user/status/{id}", this::changeStatus)  // 改变用户状态
                .DELETE("/admin/user/{ids}", this::delete)  // 删除
                .build();
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        UserQuery userQuery = new UserQuery(request.queryParams());
        return userService.listPage(userQuery)
                .flatMap(page -> Flux.fromIterable(page.getItems())
                        .<UserDto>map(item -> new UserDto().convertFrom(item))
                        .collectList()
                        .map(list -> new PageDomain<>(page.getPage(), page.getSize(), page.getTotal(), list)))
                .flatMap(super::success);
    }

    private Mono<ServerResponse> info(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return userService.getById(id)
                .<UserDto>map(item -> new UserDto().convertFrom(item))
                .flatMap(super::success);
    }

    private Mono<ServerResponse> search(ServerRequest request) {
        String keyword = request.queryParam("keyword").orElse("");
        return userService.searchDtoByKeyword(keyword)
                .collectList()
                .flatMap(super::success);
    }

    private Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(UserEditParam.class)
                .map(param -> {
                    UserEntity user = param.convertTo();
                    user.setUserUuid(UUIDUtils.fastUUID().toString());
                    user.setStatus(UserStatusEnum.Enable.getCode());
                    if (StringUtils.isNotEmpty(user.getPassword())) {
                        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
                    }
                    return user;
                })
                .flatMap(userService::editToAll)
                .then(super.success());
    }

    private Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(UserEditParam.class)
                .map(param -> {
                    UserEntity user = param.convertTo();
                    if (StringUtils.isNotEmpty(user.getPassword())) {
                        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
                    }
                    return user;
                })
                .flatMap(userService::editToAll)
                .then(super.success());
    }

    private Mono<ServerResponse> delete(ServerRequest request) {
        List<Long> ids = Arrays.stream(request.pathVariable("ids").split(","))
                .map(Long::parseLong).toList();
        return userService.removeToAllByIds(ids).then(super.success());
    }

    private Mono<ServerResponse> changeStatus(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return userService.changeStatus(id).then(super.success());
    }

}
