package com.tracejp.starnight.reactor.entity.dto;


import com.tracejp.starnight.reactor.entity.ExamPaperEntity;
import com.tracejp.starnight.reactor.entity.base.IOutputConverter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p> 试卷 elastic search dto <p/>
 *
 * @author traceJP
 * @see com.tracejp.starnight.reactor.entity.ExamPaperEntity
 * @see com.tracejp.starnight.reactor.constants.ElasticSearchConstants#EXAMPAPER_INDEX_MAPPING
 * @since 2023/7/21 11:13
 */
@Data
@Accessors(chain = true)
public class SearchExamPaperDto implements IOutputConverter<SearchExamPaperDto, ExamPaperEntity> {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者id
     */
    private Long createBy;

    /**
     * 试卷名称
     */
    private String name;

    /**
     * 学科id
     */
    private Long subjectId;

    /**
     * 试卷类型 1.固定试卷 4.时段试卷 6.任务试卷
     */
    private Integer paperType;

    /**
     * 年级
     */
    private Integer gradeLevel;

    /**
     * 试卷总分
     */
    private Integer score;

    /**
     * 题目数量
     */
    private Integer questionCount;

    /**
     * 建议时长 分钟
     */
    private Integer suggestTime;

    /**
     * 时段试卷 开始时间
     */
    private Date limitStartTime;

    /**
     * 时段试卷 结束时间
     */
    private Date limitEndTime;

}
