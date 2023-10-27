package com.tracejp.starnight.reactor.entity.param.query;

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

    public String getRealName() {
        return queryParams.getFirst("realName");
    }

    public String getPhone() {
        return queryParams.getFirst("phone");
    }

    public Integer getRole() {
        String role = queryParams.getFirst("role");
        if (role == null) {
            return null;
        }
        return Integer.parseInt(role);
    }


    @Override
    public UserEntity toEntity() {
        return new UserEntity()
                .setUserName(getUserName())
                .setRealName(getRealName())
                .setPhone(getPhone())
                .setRole(getRole());
    }

}
