package com.tracejp.starnight.reactor.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author traceJP
 * @since 2023-05-20 23:37:40
 */
@Data
@Accessors(chain = true)
@Table("t_subject")
public class SubjectEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    private Long id;

    /**
     * 删除标志位
     */
    private Boolean delFlag;

    /**
     * 学科名
     */
    private String name;

    /**
     * 年级
     */
    private Integer level;

    /**
     * 年级名
     */
    private String levelName;

    /**
     * 排序字段
     */
    private Integer itemOrder;

}
