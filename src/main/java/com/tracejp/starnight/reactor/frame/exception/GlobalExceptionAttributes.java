package com.tracejp.starnight.reactor.frame.exception;

import com.tracejp.starnight.reactor.entity.base.R;
import com.tracejp.starnight.reactor.exception.AbsBaseException;
import com.tracejp.starnight.reactor.exception.ServiceException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;


import java.util.HashMap;
import java.util.Map;

/**
 * <p> 异常属性分类 <p/>
 *
 * @author traceJP
 * @since 2023/9/18 16:56
 */
@Component
public class GlobalExceptionAttributes extends DefaultErrorAttributes {

    public static final String RESULT = "result";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = new HashMap<>();
        R<?> result = null;
        var error = getError(request);

        if (error instanceof ServiceException serverException) {
            result = R.fail(serverException.getCode(), error.getMessage());
        } else if (error instanceof AbsBaseException absBaseException) {
            result = R.fail("500", absBaseException.getMessage());
        }
        errorAttributes.putIfAbsent(RESULT, result);
        return errorAttributes;
    }

}
