package com.tracejp.starnight.reactor.service.impl;

import com.tracejp.starnight.reactor.dao.UserDao;
import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.PageResult;
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
    public Mono<PageResult<UserEntity>> findPage(int pageNum, int pageSize) {
        return findPage(new UserEntity(), pageNum, pageSize);
    }

}
