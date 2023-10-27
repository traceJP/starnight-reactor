package com.tracejp.starnight.reactor.frame.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

/**
 * <p> 全局异常控制类 <p/>
 *
 * @author traceJP
 * @since 2023/9/18 16:45
 */
//@Component
//@Order(-2)
public class GlobalExceptionController extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionController(ErrorAttributes errorAttributes,
                                     WebProperties properties,
                                     ServerCodecConfigurer serverCodecConfigurer,
                                     ApplicationContext applicationContext) {
        super(errorAttributes, properties.getResources(), applicationContext);
        super.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        var result = getErrorAttributes(request, ErrorAttributeOptions.defaults())
                .get(GlobalExceptionAttributes.RESULT);
        return ServerResponse.ok().bodyValue(result);
    }

}
