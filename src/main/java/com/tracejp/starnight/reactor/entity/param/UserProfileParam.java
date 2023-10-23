package com.tracejp.starnight.reactor.entity.param;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.IInputConverter;

import java.util.Date;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/10/23 15:42
 */
public record UserProfileParam(String realName, String phone, Integer age, Integer sex, Date birthDay)
        implements IInputConverter<UserEntity> {
}
