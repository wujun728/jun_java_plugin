package com.demo.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/*
 * @Description: 企业成员详细信息
 * @version V1.0
 */
public class QYUserDetail extends BaseWeixinResponse implements Serializable {
    @JSONField(name = "userid")
    private String userId; // 成员UserID。对应管理端的帐号
    private String name; // 成员名称
    private String mobile; // 手机号码
    private List<Integer> department; // 成员所属部门id列表
    private List<Integer> order; // 部门内的排序值，默认为0。数量必须和department一致，数值越大排序越前面。值范围是[0, 2^32)
    private String position; // 职位信息
    private Integer gender;// 性别。0表示未定义，1表示男性，2表示女性
    private String email; // 邮箱

    @JSONField(name = "isleader")
    private Boolean isLeader; // 是否为上级
    private String avatar; // 头像url。注：如果要获取小图将url最后的"/0"改成"/64"即可
    private String telephone; // 座机号

    @JSONField(name = "english_name")
    private String englishName; // 英文名

    private int status; // 关注状态: 1=已关注，2=已冻结，4=未关注
    private Map<String, String> extattr;// 	扩展属性

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getDepartment() {
        return department;
    }

    public void setDepartment(List<Integer> department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getOrder() {
        return order;
    }

    public void setOrder(List<Integer> order) {
        this.order = order;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Boolean getLeader() {
        return isLeader;
    }

    public void setLeader(Boolean leader) {
        isLeader = leader;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<String, String> getExtattr() {
        return extattr;
    }

    public void setExtattr(Map<String, String> extattr) {
        this.extattr = extattr;
    }
}
