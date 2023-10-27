package com.tracejp.starnight.reactor.dao;

import com.tracejp.starnight.reactor.entity.SubjectEntity;
import com.tracejp.starnight.reactor.entity.param.query.SubjectQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 15:48
 */
public interface SubjectDao extends R2dbcRepository<SubjectEntity, Long> {

    @Query("""
            SELECT * FROM t_subject
            WHERE (:#{#query.name} IS NULL OR name LIKE CONCAT('%', :#{#query.name}, '%'))
            AND (:#{#query.level} IS NULL OR level = :#{#query.l})
            AND del_flag = false
            """)
    Flux<SubjectEntity> listPage(SubjectQuery query, Pageable pageable);

}
