package com.chentongwei.core.system.entity.io;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 系统日志
 */
@Data
public class SysLogIO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;
    /** 操作人id */
    private Long operatorId;
    /** 一级菜单 */
    private String menu;
    /** 操作内容 */
    private String content;
    /** 客户端ip */
    private String clientIp;

}
