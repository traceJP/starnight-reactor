package com.tracejp.starnight.reactor.controller;

import com.tracejp.starnight.reactor.entity.base.R;
import com.tracejp.starnight.reactor.frame.router.IRouteEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 10:17
 */
public abstract class BaseController implements IRouteEndpoint {

    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 返回成功
     */
    public Mono<ServerResponse> success() {
        return R.ok("操作成功").flatMap(ServerResponse.ok()::bodyValue);
    }

    /**
     * 返回成功对象
     */
    protected Mono<ServerResponse> success(Object data) {
        return R.ok(data).flatMap(ServerResponse.ok()::bodyValue);
    }

    /**
     * 返回成功消息
     */
    public Mono<ServerResponse> success(String message) {
        return R.ok(message).flatMap(ServerResponse.ok()::bodyValue);
    }

    /**
     * 返回失败
     */
    public Mono<ServerResponse> error() {
        return R.fail("操作失败").flatMap(ServerResponse.ok()::bodyValue);
    }

    /**
     * 返回失败消息
     */
    public Mono<ServerResponse> error(String message) {
        return R.fail(message).flatMap(ServerResponse.ok()::bodyValue);
    }

}
