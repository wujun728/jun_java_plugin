package com.cosmoplat.entity.sys.vo.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * HomeRespVO
 *
 * @author wenbin
 * @version V1.0
 * @date 2020年3月18日
 */
@Data
public class HomeRespVO implements Serializable {
    private UserInfoRespVO userInfo;
    private List<PermissionRespNode> menus;

}