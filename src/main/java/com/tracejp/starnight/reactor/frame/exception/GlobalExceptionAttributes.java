package com.tracejp.starnight.reactor.frame.exception;

import com.tracejp.starnight.reactor.entity.base.R;
import com.tracejp.starnight.reactor.entity.enums.SystemCodeEnum;
import com.tracejp.starnight.reactor.exception.ServiceException;
import com.tracejp.starnight.reactor.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.Collections;
import java.util.Map;

/**
 * <p> 异常属性分类 <p/>
 *
 * @author traceJP
 * @since 2023/9/18 16:56
 */
@Slf4j
@Component
public class GlobalExceptionAttributes extends DefaultErrorAttributes {

    public static final String RESULT = "result";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        R<?> result;
        var error = getError(request);
        var requestURI = request.uri().toString();
        log.error("GlobalExceptionAttributes: {}", error.getMessage());

        // 请求方式不支持
        if (error instanceof MethodNotAllowedException e) {
            log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getHttpMethod());
            result = R.fail(e.getMessage());
            return Collections.singletonMap(RESULT, result);
        }

        // 业务异常
        if (error instanceof ServiceException e) {
            var code = e.getCode();
            result = StringUtils.isNull(code) ? R.fail(e.getMessage()) : R.fail(code, e.getMessage());
            return Collections.singletonMap(RESULT, result);
        }

        // 未登录
        if (error instanceof BadCredentialsException e) {
            SystemCodeEnum status = SystemCodeEnum.UNAUTHORIZED;
            result = R.fail(status.getCode(), e.getMessage());
            return Collections.singletonMap(RESULT, result);
        }

        // 运行时异常
        if (error instanceof RuntimeException e) {
            log.error("请求地址'{}',发生未知异常.", requestURI, e);
            result = R.fail(e.getMessage());
            return Collections.singletonMap(RESULT, result);
        }

        // 系统异常
        if (error instanceof Exception e) {
            log.error("请求地址'{}',发生系统异常.", requestURI, e);
            result = R.fail(e.getMessage());
            return Collections.singletonMap(RESULT, result);
        }

        return Collections.singletonMap(RESULT, null);
    }

}
