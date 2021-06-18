package com.demo.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/*
 * @Description: 部门成员
 * @version V1.0
 */
public class QYUserList extends BaseWeixinResponse {
    @JSONField(name = "userlist")
    private List<QYUserDetail> userList;

    public List<QYUserDetail> getUserList() {
        return userList;
    }

    public void setUserList(List<QYUserDetail> userList) {
        this.userList = userList;
    }
}
