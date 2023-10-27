package com.tracejp.starnight.reactor.controller.global;

import com.tracejp.starnight.reactor.controller.BaseController;
import com.tracejp.starnight.reactor.exception.ControllerException;
import com.tracejp.starnight.reactor.frame.router.IRouteEndpoint;
import com.tracejp.starnight.reactor.handler.file.IFileHandler;
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
 * @since 2023/10/24 8:55
 */
@RequiredArgsConstructor
@RestController
public class FileController extends BaseController implements IRouteEndpoint {

    private final IFileHandler fileHandler;

    @Override
    public RouterFunction<ServerResponse> endpoint() {
        return RouterFunctions.route()
                .GET("/file/sign", this::sign)
                .build();
    }

    private Mono<ServerResponse> sign(ServerRequest request) {
        var fileKey = request.queryParam("fileKey")
                .orElseThrow(() -> new ControllerException("文件key不能为空"));
        var params = request.queryParams();
        return fileHandler.uploadPreSign(fileKey, params.toSingleValueMap())
                .flatMap(this::success);
    }

}
