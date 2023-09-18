package com.tracejp.starnight.reactor.service.impl;

import com.tracejp.starnight.reactor.entity.base.PageDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p> 基础业务类 <p/>
 *
 * @author traceJP
 * @since 2023/9/13 10:33
 */
public abstract class BaseService<Dao extends R2dbcRepository<Domain, Long>, Domain> {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Dao层对象
     */
    @Autowired
    protected Dao dao;

    /**
     * 获取单条数据
     */
    public Mono<Domain> get(Long id) {
        return dao.findById(id);
    }

    /**
     * 获取单条数据
     */
    public Mono<Domain> get(Domain entity) {
        return dao.findOne(Example.of(entity));
    }

    /**
     * 查询列表数据
     */
    public Flux<Domain> findList(Domain entity) {
        return dao.findAll(Example.of(entity));
    }

    /**
     * 查询分页数据 - 手动分页
     */
    public Mono<PageDomain<Domain>> findPage(Domain entity, int page, int size) {
        var list = findList(entity);
        var count = list.count();
        if (page > 0) {
            list = list.skip(((long) (page - 1)) * (long) size);
        }
        if (size > 0) {
            list = list.take(size);
        }
        return list.collectList().zipWith(count)
                .map(tuple -> {
                    var content = tuple.getT1();
                    var total = tuple.getT2();
                    return new PageDomain<>(page, size, total, content);
                });
    }

    /**
     * 保存数据（插入或更新）
     */
    public Mono<Domain> saveOrUpdate(Domain entity) {
        return dao.save(entity);
    }

    // TODO 修改需要排除 null 值

    /**
     * 删除数据
     */
    public Mono<Void> delete(Domain entity) {
        return dao.delete(entity);
    }

}
