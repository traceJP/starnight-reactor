package com.tracejp.starnight.reactor.service.impl;

import com.tracejp.starnight.reactor.dao.UserDao;
import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.PageDomain;
import com.tracejp.starnight.reactor.exception.ServiceException;
import com.tracejp.starnight.reactor.service.IUserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/13 10:32
 */
@Service
public class UserServiceImpl extends BaseService<UserDao, UserEntity> implements IUserService {

    @Override
    public Mono<PageDomain<UserEntity>> findPage(int pageNum, int pageSize) {
        return findPage(new UserEntity(), pageNum, pageSize);
    }

    @Override
    public Mono<UserEntity> findByUserName(String username) {
        return get(new UserEntity().setUserName(username));
    }

    @Override
    public Mono<UserEntity> updateById(UserEntity user) {
        if (user.getId() == null) {
            return Mono.error(new ServiceException("用户ID不能为空"));
        }
        return saveOrUpdate(user);
    }

}
