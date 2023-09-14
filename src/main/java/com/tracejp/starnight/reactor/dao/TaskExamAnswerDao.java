package com.tracejp.starnight.reactor.dao;

import com.tracejp.starnight.reactor.entity.TaskExamAnswerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 15:49
 */
public interface TaskExamAnswerDao extends R2dbcRepository<TaskExamAnswerEntity, Long> {
}
