package com.tracejp.starnight.reactor.service.impl;

import com.tracejp.starnight.reactor.dao.SubjectDao;
import com.tracejp.starnight.reactor.entity.SubjectEntity;
import com.tracejp.starnight.reactor.entity.base.PageDomain;
import com.tracejp.starnight.reactor.entity.param.query.SubjectQuery;
import com.tracejp.starnight.reactor.service.ISubjectService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/14 16:57
 */
@Service
public class SubjectServiceImpl extends BaseService<SubjectDao, SubjectEntity> implements ISubjectService {

    @Override
    public Mono<PageDomain<SubjectEntity>> listPage(SubjectQuery query) {
        return getPageDomain(() -> dao.listPage(query, query.getPageRequest()), query);
    }

    @Override
    public Flux<SubjectEntity> listByLevel(Integer level) {
        return findList(new SubjectEntity().setLevel(level));
    }

    @Override
    public Mono<SubjectEntity> getById(Long id) {
        return get(new SubjectEntity().setId(id).setDelFlag(false));
    }

    @Override
    public Mono<Boolean> edit(SubjectEntity subjectEntity) {
        return saveOrUpdate(subjectEntity).hasElement();
    }

    @Override
    public Mono<Boolean> removeByIds(List<Long> ids) {
        return dao.deleteAllById(ids).hasElement();
    }

}
