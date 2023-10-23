package com.tracejp.starnight.reactor.search;

import com.tracejp.starnight.reactor.entity.dto.SearchUserDto;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/10/23 10:04
 */
public interface UserDtoSearchRepository extends ReactiveElasticsearchRepository<SearchUserDto, Long> {
}
