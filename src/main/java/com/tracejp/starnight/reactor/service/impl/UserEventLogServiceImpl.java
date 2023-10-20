package com.tracejp.starnight.reactor.service.impl;

import com.tracejp.starnight.reactor.dao.UserEventLogDao;
import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.UserEventLogEntity;
import com.tracejp.starnight.reactor.service.IUserEventLogService;
import com.tracejp.starnight.reactor.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/14 16:58
 */
@RequiredArgsConstructor
@Service
public class UserEventLogServiceImpl extends BaseService<UserEventLogDao, UserEventLogEntity> implements IUserEventLogService {

    @Override
    public Mono<Boolean> save(UserEventLogEntity userEventLogEntity) {
        return saveOrUpdate(userEventLogEntity).hasElement();
    }

    @Override
    public Mono<Boolean> save(UserEntity user, String content) {
        UserEventLogEntity eventLogEntity = new UserEventLogEntity()
                .setUserId(user.getId())
                .setUserName(user.getUserName())
                .setRealName(user.getRealName())
                .setContent(content);
        return save(eventLogEntity);
    }

    @Override
    public Mono<Boolean> save(String content) {
        return SecurityUtils.getLoginUser()
                .flatMap(loginUser -> save(loginUser.getUser(), content));
    }

}
