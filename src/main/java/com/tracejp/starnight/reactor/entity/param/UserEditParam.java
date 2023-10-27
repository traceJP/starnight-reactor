package com.tracejp.starnight.reactor.entity.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.IInputConverter;

import java.util.Date;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/10/27 11:29
 */
public record UserEditParam(Long id,
                            String userName,
                            String password,
                            String realName,
                            Integer age,
                            Integer sex,
                            @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
                            Date birthDay,
                            String phone,
                            Integer role,
                            Integer status)
        implements IInputConverter<UserEntity> {
}
