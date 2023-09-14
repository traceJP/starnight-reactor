package com.tracejp.starnight.reactor.dao;

import com.tracejp.starnight.reactor.entity.QuestionEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 15:48
 */
public interface QuestionDao extends R2dbcRepository<QuestionEntity, Long> {
}
