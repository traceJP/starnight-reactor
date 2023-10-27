package com.tracejp.starnight.reactor.dao;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.param.UserQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 10:30
 */
@Repository
public interface UserDao extends R2dbcRepository<UserEntity, Long> {

    /**
     * 列表
     */
    @Query("""
            SELECT * FROM t_user
                 WHERE (:#{#userQuery.userName} IS NULL OR user_name LIKE CONCAT('%', :#{#userQuery.userName}, '%'))
                 AND (:#{#userQuery.realName} IS NULL OR real_name LIKE CONCAT('%', :#{#userQuery.realName}, '%'))
                 AND (:#{#userQuery.phone} IS NULL OR phone LIKE CONCAT('%', :#{#userQuery.phone}, '%'))
                 AND (:#{#userQuery.role} IS NULL OR role = :#{#userQuery.role})
                 AND del_flag = false
            """)
    Flux<UserEntity> listPage(@Param("userQuery") UserQuery userQuery, Pageable pageable);

}
