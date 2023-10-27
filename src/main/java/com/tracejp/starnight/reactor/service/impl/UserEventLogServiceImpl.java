package com.tracejp.starnight.reactor.service.impl;

import com.tracejp.starnight.reactor.dao.UserEventLogDao;
import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.UserEventLogEntity;
import com.tracejp.starnight.reactor.entity.base.PageDomain;
import com.tracejp.starnight.reactor.entity.param.query.UserEventLogQuery;
import com.tracejp.starnight.reactor.service.IUserEventLogService;
import com.tracejp.starnight.reactor.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
    public Mono<Boolean> saveAsync(UserEventLogEntity userEventLogEntity) {
        return saveOrUpdate(userEventLogEntity)
                .hasElement()
                .doOnError(e -> logger.error("保存用户事件日志失败: {}, 日志: {}", e, userEventLogEntity))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Boolean> saveAsync(UserEntity user, String content) {
        UserEventLogEntity eventLogEntity = new UserEventLogEntity()
                .setUserId(user.getId())
                .setUserName(user.getUserName())
                .setRealName(user.getRealName())
                .setContent(content);
        return saveAsync(eventLogEntity);
    }

    @Override
    public Mono<Boolean> saveAsync(String content) {
        return SecurityUtils.getLoginUser()
                .flatMap(loginUser -> saveAsync(loginUser.getUser(), content));
    }

    @Override
    public Mono<PageDomain<UserEventLogEntity>> listPage(UserEventLogQuery query) {
        return findList(query);
    }

}
