package com.tracejp.starnight.reactor.entity;

import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
import java.util.List;

/**
 * @author traceJP
 * @since 2023-05-20 23:37:40
 */
@Data
@Accessors(chain = true)
@Table("t_text_content")
public class TextContentEntity {

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
     * 文本内容
     */
    private String content;

    public <T> void setContent(T content) {
        this.content = JSON.toJSONString(content);
    }

    public <T> T getContent(Class<T> clazz) {
        return JSON.parseObject(JSON.parse(content).toString(), clazz);
    }

    public <T> List<T> getContentArray(Class<T> clazz) {
        return JSON.parseArray(JSON.parse(content).toString(), clazz);
    }

}
