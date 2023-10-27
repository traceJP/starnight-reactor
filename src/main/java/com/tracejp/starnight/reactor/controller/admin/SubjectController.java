package com.tracejp.starnight.reactor.controller.admin;

import com.tracejp.starnight.reactor.controller.BaseController;
import com.tracejp.starnight.reactor.entity.SubjectEntity;
import com.tracejp.starnight.reactor.entity.param.query.SubjectQuery;
import com.tracejp.starnight.reactor.service.ISubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/10/27 16:38
 */
@RequiredArgsConstructor
@RestController
public class SubjectController extends BaseController {

    private final ISubjectService subjectService;

    @Override
    public RouterFunction<ServerResponse> endpoint() {
        return RouterFunctions.route()
                .GET("/admin/subject/list", this::list)  // 列表
                .GET("/admin/subject/list/all", this::listByLevel)  // 列表所有
                .GET("/admin/subject/{id}", this::info)  // 信息
                .POST("/admin/subject", this::edit)  // 保存
                .PUT("/admin/subject", this::edit)  // 修改
                .DELETE("/admin/subject/{ids}", this::delete)  // 删除
                .build();
    }

    private Mono<ServerResponse> list(ServerRequest request) {
        SubjectQuery query = new SubjectQuery(request.queryParams());
        return subjectService.listPage(query)
                .flatMap(super::success);
    }

    private Mono<ServerResponse> listByLevel(ServerRequest request) {
        Integer level = request.queryParam("level").map(Integer::parseInt).orElse(null);
        return subjectService.listByLevel(level)
                .collectList().flatMap(super::success);
    }

    private Mono<ServerResponse> info(ServerRequest request) {
        Long id = Long.parseLong(request.pathVariable("id"));
        return subjectService.getById(id)
                .flatMap(super::success);
    }

    private Mono<ServerResponse> edit(ServerRequest request) {
        return request.bodyToMono(SubjectEntity.class)
                .flatMap(subjectService::edit)
                .flatMap(super::success);
    }

    private Mono<ServerResponse> delete(ServerRequest request) {
        List<Long> ids = Arrays.stream(request.pathVariable("ids").split(","))
                .map(Long::parseLong).toList();
        return subjectService.removeByIds(ids).flatMap(super::success);
    }

}
