package com.tracejp.starnight.reactor.service;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.PageDomain;
import com.tracejp.starnight.reactor.entity.param.UserQuery;
import reactor.core.publisher.Mono;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 10:32
 */
public interface IUserService {

    /**
     * 列表
     */
    Mono<PageDomain<UserEntity>> listPage(UserQuery userQuery);

    /**
     * 通过用户名查询用户实体
     */
    Mono<UserEntity> getByUserName(String username);

    /**
     * 通过用户ID修改用户实体
     */
    Mono<UserEntity> updateById(UserEntity user);

    /**
     * 保存/更新 用户 - 级联保存
     */
    Mono<Void> editToAll(UserEntity user);

}
