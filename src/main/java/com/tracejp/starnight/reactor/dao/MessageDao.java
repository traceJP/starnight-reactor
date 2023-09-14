package com.tracejp.starnight.reactor.dao;

import com.tracejp.starnight.reactor.entity.MessageEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 15:48
 */
public interface MessageDao extends R2dbcRepository<MessageEntity, Long> {
}
