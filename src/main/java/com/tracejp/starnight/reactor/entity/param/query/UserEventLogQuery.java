package com.tracejp.starnight.reactor.entity.param.query;

import com.tracejp.starnight.reactor.entity.UserEventLogEntity;
import com.tracejp.starnight.reactor.entity.base.IQueryPageRequest;
import org.springframework.util.MultiValueMap;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/10/27 16:27
 */
public class UserEventLogQuery extends IQueryPageRequest.QueryPageRequest<UserEventLogEntity> {

    public UserEventLogQuery(MultiValueMap<String, String> queryParams) {
        super(queryParams);
    }

    public Long getId() {
        String id = queryParams.getFirst("id");
        if (id == null) {
            return null;
        }
        return Long.parseLong(id);
    }

    public String getUserName() {
        return queryParams.getFirst("userName");
    }

    public String getRealName() {
        return queryParams.getFirst("realName");
    }

    @Override
    public UserEventLogEntity toEntity() {
        return new UserEventLogEntity()
                .setId(getId())
                .setUserName(getUserName())
                .setRealName(getRealName());
    }

}
