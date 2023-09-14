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
@Table("t_user_event_log")
public class UserEventLogEntity {

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
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 操作记录
     */
    private String content;

}
