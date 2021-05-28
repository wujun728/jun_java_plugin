package com.chentongwei.core.system.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 系统日志
 */
@Data
public class SysLog implements Serializable {
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
    /** 创建时间 */
    private Date createTime;

}
