package com.chentongwei.entity.common.io;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增反馈IO
 *
 * @author TongWei.Chen 2017-06-21 14:41:19
 */
public class FeedbackIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    /** 标题 */
    private String title;
    @NotEmpty
    @NotNull
    @NotBlank
    /** 反馈内容 */
    private String content;
    /** 创建人id */
    private Long creatorId;
    /** 创建人 */
    private String creator;
    /** ip */
    private String ip;
    /** 验证码 */
    @NotEmpty
    @NotNull
    @NotBlank
    private String authCode;
    /** token */
    @NotEmpty
    @NotNull
    @NotBlank
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
