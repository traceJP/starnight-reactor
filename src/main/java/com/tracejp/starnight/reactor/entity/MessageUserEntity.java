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
@Table("t_message_user")
public class MessageUserEntity {

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
     * 消息内容id
     */
    private Long messageId;

    /**
     * 接收人id
     */
    private Long receiveUserId;

    /**
     * 接收人用户名
     */
    private String receiveUserName;

    /**
     * 接收人真实姓名
     */
    private String receiveRealName;

    /**
     * 是否已读
     */
    private Boolean readed;

    /**
     * 阅读时间
     */
    private Date readTime;

}
