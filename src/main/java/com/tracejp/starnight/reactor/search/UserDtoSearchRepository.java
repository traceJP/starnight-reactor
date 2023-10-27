package com.tracejp.starnight.reactor.search;

import com.tracejp.starnight.reactor.entity.dto.SearchUserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Flux;

/**
 * <p>  <p/>
 *
 * @author traceJP
 * @since 2023/10/23 10:04
 */
public interface UserDtoSearchRepository extends ReactiveElasticsearchRepository<SearchUserDto, Long> {

    @Query("""
            {
                "bool": {
                    "should": [
                        {
                            "match": {
                                "userName": {
                                    "query": ":keyword",
                                    "boost": 3
                                }
                            }
                        },
                        {
                            "match": {
                                "nickName": {
                                    "query": ":keyword",
                                    "boost": 2
                                }
                            }
                        },
                        {
                            "match": {
                                "phone": {
                                    "query": ":keyword",
                                    "boost": 1
                                }
                            }
                        }
                    ]
                }
            }
            """)
    Flux<SearchUserDto> searchByKeyword(String keyword, Pageable pageable);

}
