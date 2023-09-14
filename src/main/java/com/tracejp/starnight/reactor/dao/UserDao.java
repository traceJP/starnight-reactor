package com.tracejp.starnight.reactor.dao;

import com.tracejp.starnight.reactor.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 10:30
 */
@Repository
public interface UserDao extends R2dbcRepository<UserEntity, Long> {
}
