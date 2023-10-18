package com.tracejp.starnight.reactor.entity.param;

import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.IQueryPageRequest;
import org.springframework.util.MultiValueMap;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/10/18 11:04
 */
public class UserQuery extends IQueryPageRequest.QueryPageRequest<UserEntity> {

    public UserQuery(MultiValueMap<String, String> queryParams) {
        super(queryParams);
    }

    public String getUserName() {
        return queryParams.getFirst("userName");
    }


    @Override
    public UserEntity toEntity() {
        return new UserEntity()
                .setUserName(getUserName());
    }

}