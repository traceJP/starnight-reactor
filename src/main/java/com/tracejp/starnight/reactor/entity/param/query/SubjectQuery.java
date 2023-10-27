package com.tracejp.starnight.reactor.entity.param.query;

import com.tracejp.starnight.reactor.entity.SubjectEntity;
import com.tracejp.starnight.reactor.entity.base.IQueryPageRequest;
import org.springframework.util.MultiValueMap;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/10/27 16:40
 */
public class SubjectQuery extends IQueryPageRequest.QueryPageRequest<SubjectEntity> {

    public SubjectQuery(MultiValueMap<String, String> queryParams) {
        super(queryParams);
    }

    public String getName() {
        return queryParams.getFirst("name");
    }

    public Integer getLevel() {
        String level = queryParams.getFirst("level");
        if (level == null) {
            return null;
        }
        return Integer.parseInt(level);
    }

    @Override
    public SubjectEntity toEntity() {
        return new SubjectEntity()
                .setName(getName())
                .setLevel(getLevel());
    }

}
