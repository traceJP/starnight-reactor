package com.tracejp.starnight.reactor.service;

import com.tracejp.starnight.reactor.entity.SubjectEntity;
import com.tracejp.starnight.reactor.entity.base.PageDomain;
import com.tracejp.starnight.reactor.entity.param.query.SubjectQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/14 16:53
 */
public interface ISubjectService {

    Mono<PageDomain<SubjectEntity>> listPage(SubjectQuery query);

    Flux<SubjectEntity> listByLevel(Integer level);

    Mono<SubjectEntity> getById(Long id);

    Mono<Boolean> edit(SubjectEntity subjectEntity);

    Mono<Boolean> removeByIds(List<Long> ids);

}
