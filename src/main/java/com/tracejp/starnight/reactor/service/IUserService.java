package com.tracejp.starnight.reactor.service;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.PageResult;
import reactor.core.publisher.Mono;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 10:32
 */
public interface IUserService {

    Mono<PageResult<UserEntity>> findPage(int pageNum, int pageSize);

}
