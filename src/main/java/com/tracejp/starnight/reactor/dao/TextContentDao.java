package com.tracejp.starnight.reactor.dao;

import com.tracejp.starnight.reactor.entity.TextContentEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 15:50
 */
public interface TextContentDao extends R2dbcRepository<TextContentEntity, Long> {
}
