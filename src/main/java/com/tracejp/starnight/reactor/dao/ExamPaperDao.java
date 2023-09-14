package com.tracejp.starnight.reactor.dao;

import com.tracejp.starnight.reactor.entity.ExamPaperEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 15:47
 */
public interface ExamPaperDao extends R2dbcRepository<ExamPaperEntity, Long> {
}
