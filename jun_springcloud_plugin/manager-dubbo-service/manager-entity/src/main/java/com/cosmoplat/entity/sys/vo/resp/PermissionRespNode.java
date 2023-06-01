package com.cosmoplat.entity.sys.vo.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * PermissionRespNode
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class PermissionRespNode implements Serializable {
    private String id;
    private String name;
    private String url;

    private String perms;
    private String icon;

    private String target;

    private String pid;

    private String pidName;

    private Integer type;

    private Integer orderNum;

    private boolean spread = true;

    private boolean checked;
    private List<?> children;

    private Integer unableFlag;
}
