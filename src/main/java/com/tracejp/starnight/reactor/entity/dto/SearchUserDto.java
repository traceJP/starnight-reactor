package com.tracejp.starnight.reactor.entity.dto;


import com.tracejp.starnight.reactor.constants.ElasticSearchConstants;
import com.tracejp.starnight.reactor.entity.UserEntity;
import com.tracejp.starnight.reactor.entity.base.IOutputConverter;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * <p> 用户搜索实体 <p/>
 *
 * @author traceJP
 * @see com.tracejp.starnight.reactor.entity.UserEntity
 * @see com.tracejp.starnight.reactor.constants.ElasticSearchConstants#USER_INDEX_MAPPING
 * @since 2023/7/20 10:58
 */
@Data
@Accessors(chain = true)
@Document(indexName = ElasticSearchConstants.USER_INDEX)
public class SearchUserDto implements IOutputConverter<SearchUserDto, UserEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增 id
     */
    @Id
    @Field(type = FieldType.Long)
    private Long id;

    /**
     * 用户名
     */
    @Field(type = FieldType.Keyword)
    private String userName;

    /**
     * 真实姓名
     */
    @Field(type = FieldType.Keyword)
    private String realName;

    /**
     * 手机号
     */
    @Field(type = FieldType.Text)
    private String phone;

}
