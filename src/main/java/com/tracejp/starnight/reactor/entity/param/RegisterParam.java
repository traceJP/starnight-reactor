package com.tracejp.starnight.reactor.entity.param;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.IInputConverter;

/**
 * <p> 注册表单 <p/>
 *
 * @author traceJP
 * @since 2023/7/13 10:26
 */
public record RegisterParam(Integer userLevel, String userName, String password)
        implements IInputConverter<UserEntity> {
}
