package com.cosmoplat.entity.sys.vo.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * LoginRespVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class LoginRespVO implements Serializable {
    private String accessToken;
    private String username;
    private String realName;
    private String id;
    private String phone;
    private List<PermissionRespNode> menus;
    private Set<String> permissions;
    private List<Map<String, String>> urls;
}
