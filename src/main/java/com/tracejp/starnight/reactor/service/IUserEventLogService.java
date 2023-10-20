package com.tracejp.starnight.reactor.service;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.UserEventLogEntity;
import reactor.core.publisher.Mono;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/14 16:54
 */
public interface IUserEventLogService {

    /**
     * 异步保存
     */
    Mono<Boolean> save(UserEventLogEntity userEventLogEntity);

    /**
     * 异步保存
     */
    Mono<Boolean> save(UserEntity user, String content);

    /**
     * 异步保存当前用户的事件日志
     */
    Mono<Boolean> save(String content);

}
