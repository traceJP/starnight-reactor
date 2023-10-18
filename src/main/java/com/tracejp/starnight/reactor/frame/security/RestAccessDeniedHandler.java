package com.tracejp.starnight.reactor.frame.security;

import com.tracejp.starnight.reactor.entity.enums.SystemCodeEnum;
import com.tracejp.starnight.reactor.utils.ServerUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p> 权限拦截响应 <p/>
 *
 * @author traceJP
 * @since 2023/5/24 21:16
 */
@Component
public class RestAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        SystemCodeEnum code = SystemCodeEnum.AccessDenied;
        return ServerUtils.webFluxResponseWriter(exchange.getResponse(), code.getMessage(), code.getCode());
    }

}
