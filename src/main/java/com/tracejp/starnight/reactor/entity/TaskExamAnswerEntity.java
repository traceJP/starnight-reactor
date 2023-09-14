package com.tracejp.starnight.reactor.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

/**
 * @author traceJP
 * @since 2023-05-20 23:37:40
 */
@Data
@Accessors(chain = true)
@Table("t_task_exam_answer")
public class TaskExamAnswerEntity {

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
     * 创建者id（任务完成者id 学生id）
     */
    private Long createBy;

    /**
     * 任务id
     */
    private Long taskExamId;

    /**
     * 任务完成情况id - List<TaskItemAnswerPo>
     */
    private Long textContentId;

}
