package com.tracejp.starnight.reactor.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * @author traceJP
 * @since 2023-05-20 23:37:41
 */
@Data
@Accessors(chain = true)
@Table("t_exam_paper")
public class ExamPaperEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    private Long id;

    /**
     * 创建时间
     */
    @CreatedDate
    private Date createTime;

    /**
     * 创建者id
     */
    private Long createBy;

    /**
     * 删除标志位
     */
    private Boolean delFlag;

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

    /**
     * 试卷内容id
     */
    private Long frameTextContentId;

    /**
     * 考试任务id
     */
    private Long taskExamId;

}
