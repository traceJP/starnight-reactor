package com.tracejp.starnight.reactor.service;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.UserEventLogEntity;
import com.tracejp.starnight.reactor.entity.base.PageDomain;
import com.tracejp.starnight.reactor.entity.param.query.UserEventLogQuery;
import reactor.core.publisher.Mono;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/14 16:54
 */
public interface IUserEventLogService {

    Mono<Boolean> saveAsync(UserEventLogEntity userEventLogEntity);

    Mono<Boolean> saveAsync(UserEntity user, String content);

    Mono<Boolean> saveAsync(String content);

    Mono<PageDomain<UserEventLogEntity>> listPage(UserEventLogQuery query);

}
