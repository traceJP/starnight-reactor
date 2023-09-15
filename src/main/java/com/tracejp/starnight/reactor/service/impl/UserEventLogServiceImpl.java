package com.tracejp.starnight.reactor.service.impl;

import com.tracejp.starnight.reactor.dao.UserEventLogDao;
import com.tracejp.starnight.reactor.entity.UserEventLogEntity;
import com.tracejp.starnight.reactor.service.IUserEventLogService;
import org.springframework.stereotype.Service;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/9/14 16:58
 */
@Service
public class UserEventLogServiceImpl extends BaseService<UserEventLogDao, UserEventLogEntity> implements IUserEventLogService {
}
