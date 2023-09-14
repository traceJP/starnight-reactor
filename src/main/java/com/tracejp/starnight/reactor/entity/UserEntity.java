package com.tracejp.starnight.reactor.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * @author traceJP
 * @since 2023-05-20 23:37:40
 */
@Data
@Accessors(chain = true)
@Table("t_user")
public class UserEntity {

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
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @LastModifiedDate
    private LocalDateTime updateTime;

    /**
     * 删除标志位
     */
    private Boolean delFlag;

    /**
     * 唯一uuid
     */
    private String userUuid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别 1.男 2女
     */
    private Integer sex;

    /**
     * 生日
     */
    private LocalDateTime birthDay;

    /**
     * 学生年级(1-12)
     */
    private Integer userLevel;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色 1.学生 2.教师 3.管理员
     */
    private Integer role;

    /**
     * 状态 1.启用 2禁用
     */
    private Integer status;

    /**
     * 头像地址
     */
    private String avatarPath;

    /**
     * 最后一次上线时间
     */
    private LocalDateTime lastActiveTime;

    /**
     * 微信openId
     */
    private String wxOpenId;

}
