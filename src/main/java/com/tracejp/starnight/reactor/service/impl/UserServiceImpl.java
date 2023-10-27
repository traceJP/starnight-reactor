package com.tracejp.starnight.reactor.service.impl;

import com.tracejp.starnight.reactor.dao.UserDao;
import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.PageDomain;
import com.tracejp.starnight.reactor.entity.dto.SearchUserDto;
import com.tracejp.starnight.reactor.entity.param.UserQuery;
import com.tracejp.starnight.reactor.exception.ServiceException;
import com.tracejp.starnight.reactor.search.UserDtoSearchRepository;
import com.tracejp.starnight.reactor.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 10:32
 */
@RequiredArgsConstructor
@Service
public class UserServiceImpl extends BaseService<UserDao, UserEntity> implements IUserService {

    private final UserDtoSearchRepository userDtoSearchRepository;

    @Override
    public Mono<PageDomain<UserEntity>> listPage(UserQuery userQuery) {
        return getPageDomain(() -> dao.listPage(userQuery, userQuery.getPageRequest()), userQuery);
    }

    @Override
    public Mono<UserEntity> getByUserName(String username) {
        return get(new UserEntity().setUserName(username).setDelFlag(false));
    }

    @Override
    public Mono<UserEntity> updateById(UserEntity user) {
        if (user.getId() == null) {
            return Mono.error(new ServiceException("用户ID不能为空"));
        }
        user.setDelFlag(false);
        return saveOrUpdate(user);
    }

    @Transactional
    @Override
    public Mono<Void> editToAll(UserEntity user) {
        user.setDelFlag(false);
        return saveOrUpdate(user)
                .flatMap(res -> {
                    SearchUserDto dto = new SearchUserDto().convertFrom(res);
                    // TODO 这里不是根据 sql的id修改的，而是根据es的id修改的
                    // 导致如果找到了记录，会出现 Failed to convert from type [java.lang.String] to type [java.lang.Long] for value [atYUcIsBdJhYXFSvYB5c]
                    return userDtoSearchRepository.save(dto);
                })
                .then();
    }

    @Override
    public Mono<UserEntity> getById(Long id) {
        return get(new UserEntity().setId(id).setDelFlag(false));
    }

    @Override
    public Flux<SearchUserDto> searchDtoByKeyword(String keyword) {
        // , PageRequest.of(0, 10)
        return userDtoSearchRepository.searchByKeyword(keyword);
    }

    @Override
    public Mono<Void> removeToAllByIds(List<Long> ids) {
        return dao.deleteAllById(ids);
    }

    @Override
    public Mono<Boolean> changeStatus(Long id) {
        return dao.changeStatus(id);
    }


}
