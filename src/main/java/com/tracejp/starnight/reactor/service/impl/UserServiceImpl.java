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
import reactor.core.publisher.Mono;

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
        return get(new UserEntity().setUserName(username));
    }

    @Override
    public Mono<UserEntity> updateById(UserEntity user) {
        if (user.getId() == null) {
            return Mono.error(new ServiceException("用户ID不能为空"));
        }
        return saveOrUpdate(user);
    }

    @Transactional
    @Override
    public Mono<Void> editToAll(UserEntity user) {
        var daoMono = saveOrUpdate(user);
        var esMono = userDtoSearchRepository.save(new SearchUserDto().convertFrom(user));
        return Mono.when(daoMono, esMono).then();
    }

}
