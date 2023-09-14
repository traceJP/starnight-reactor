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
@Table("t_message")
public class MessageEntity {

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
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送者id
     */
    private Long sendUserId;

    /**
     * 发送者用户名
     */
    private String sendUserName;

    /**
     * 发送者真实姓名
     */
    private String sendRealName;

    /**
     * 接收人数
     */
    private Integer receiveUserCount;

    /**
     * 已读人数
     */
    private Integer readCount;

}
