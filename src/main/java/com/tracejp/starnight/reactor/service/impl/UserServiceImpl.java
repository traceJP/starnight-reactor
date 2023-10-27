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
import org.springframework.data.domain.PageRequest;
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
        var daoMono = saveOrUpdate(user);
        var esMono = userDtoSearchRepository.save(new SearchUserDto().convertFrom(user));
        return Mono.when(daoMono, esMono).then();
    }

    @Override
    public Mono<UserEntity> getById(Long id) {
        return get(new UserEntity().setId(id).setDelFlag(false));
    }

    @Override
    public Flux<SearchUserDto> searchDtoByKeyword(String keyword) {
        return userDtoSearchRepository.searchByKeyword(keyword, PageRequest.of(0, 10));
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
