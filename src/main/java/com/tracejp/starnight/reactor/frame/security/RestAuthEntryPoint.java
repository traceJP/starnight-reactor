package com.tracejp.starnight.reactor.frame.security;

import com.tracejp.starnight.reactor.entity.enums.SystemCodeEnum;
import com.tracejp.starnight.reactor.utils.ServerUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * <p> 未登录 <p/>
 *
 * @author traceJP
 * @since 2023/5/24 21:58
 */
@Component
public class RestAuthEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        SystemCodeEnum code = SystemCodeEnum.UNAUTHORIZED;
        return ServerUtils.webFluxResponseWriter(exchange.getResponse(), code.getMessage(), code.getCode());
    }

}
