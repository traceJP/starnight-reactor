package com.tracejp.starnight.reactor.service;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.PageDomain;
import reactor.core.publisher.Mono;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 10:32
 */
public interface IUserService {

    // TODO: 2023/9/13 10:32 这个分页方式属实有点呆逼
    Mono<PageDomain<UserEntity>> findPage(int pageNum, int pageSize);

    /**
     * 通过用户名查询用户实体
     */
    Mono<UserEntity> findByUserName(String username);

    /**
     * 通过用户ID修改用户实体
     */
    Mono<UserEntity> updateById(UserEntity user);

}
