package com.tracejp.starnight.reactor.dao;

import com.tracejp.starnight.reactor.entity.ExamPaperAnswerEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 15:46
 */
public interface ExamPaperAnswerDao extends R2dbcRepository<ExamPaperAnswerEntity, Long> {
}
