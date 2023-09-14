package com.tracejp.starnight.reactor.dao;

import com.tracejp.starnight.reactor.entity.UserEventLogEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 15:50
 */
public interface UserEventLogDao extends R2dbcRepository<UserEventLogEntity, Long> {
}
