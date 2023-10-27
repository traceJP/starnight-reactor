package com.tracejp.starnight.reactor.service;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.PageDomain;
import com.tracejp.starnight.reactor.entity.dto.SearchUserDto;
import com.tracejp.starnight.reactor.entity.param.UserQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 10:32
 */
public interface IUserService {

    Mono<PageDomain<UserEntity>> listPage(UserQuery userQuery);

    Mono<UserEntity> getByUserName(String username);

    Mono<UserEntity> updateById(UserEntity user);

    Mono<Void> editToAll(UserEntity user);

    Mono<UserEntity> getById(Long id);

    Flux<SearchUserDto> searchDtoByKeyword(String keyword);

    Mono<Void> removeToAllByIds(List<Long> ids);

    Mono<Boolean> changeStatus(Long id);

}
