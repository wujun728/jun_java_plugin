package com.demo.weixin.request.handlers;

import com.demo.weixin.exception.WeixinException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wujun
 * @description 创建成员
 * @date 2017/7/31
 * @since 1.0
 */
public class CreateUserRequestHandler extends AbstractRequestHandler {
    private String userId; // 必填。成员UserID。对应管理端的帐号，企业内必须唯一。不区分大小写，长度为1~64个字节
    private String name; // 必填。成员名称。长度为1~64个字节
    private String mobile; // 必填。手机号码。企业内必须唯一
    private List<Integer> department; // 必填。成员所属部门id列表,不超过20个
    private String englishName;//  英文名。长度为1-64个字节
    private List<Integer> order;// 部门内的排序值，默认为0。数量必须和department一致，数值越大排序越前面。有效的值范围是[0, 2^32)
    private String position; // 职位信息。长度为0~64个字节
    private int gender;// 性别 性别。0表示未定义，1表示男性，2表示女性
    private String email; // 邮箱。长度为0~64个字节。企业内必须唯一
    private String telephone; // 座机。长度0-64个字节。
    private boolean isLeader; // 上级字段，标识是否为上级。
    private String avatarMediaid; // 成员头像的mediaid，通过多媒体接口上传图片获得的mediaid
    private boolean enable; // 启用/禁用成员。true:1 表示启用成员，false:0 表示禁用成员
    private List<Map<String, String>> extattr;// 	自定义字段。自定义字段需要先在WEB管理端“我的企业” — “通讯录管理”添加，否则忽略未知属性的赋值


    @Override
    public Map<String, Object> getParamMap() throws WeixinException {
        // 验证必要的参数
        if (StringUtils.isAnyBlank(userId, name, mobile) || CollectionUtils.isEmpty(department)) {
            throw new WeixinException(-1,"参数缺失");
        }

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userid", userId);
        paramMap.put("name", name);
        paramMap.put("mobile", mobile);
        paramMap.put("department", department);
        paramMap.put("gender", gender + "");
        paramMap.put("isleader", isLeader ? "1" : "0");

        this.addValueFromString("english_name", englishName, paramMap);
        this.addValueFromList("order", order, paramMap);
        this.addValueFromString("position", position, paramMap);
        this.addValueFromString("email", email, paramMap);
        this.addValueFromString("telephone", telephone, paramMap);
        this.addValueFromString("avatar_mediaid", avatarMediaid, paramMap);

        if (CollectionUtils.isNotEmpty(extattr)) {
            Map<String, List<Map<String, String>>> extattrMap = new HashMap<>();
            extattrMap.put("attrs", extattr);
            paramMap.put("extattr", extattrMap);
        }
        return paramMap;
    }


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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Integer> getDepartment() {
        return department;
    }

    public void setDepartment(List<Integer> department) {
        this.department = department;
    }

    public List<Integer> getOrder() {
        return order;
    }

    public void setOrder(List<Integer> order) {
        this.order = order;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getLeader() {
        return isLeader;
    }

    public void setLeader(Boolean leader) {
        isLeader = leader;
    }


    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public String getAvatarMediaid() {
        return avatarMediaid;
    }

    public void setAvatarMediaid(String avatarMediaid) {
        this.avatarMediaid = avatarMediaid;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<Map<String, String>> getExtattr() {
        return extattr;
    }

    public void setExtattr(List<Map<String, String>> extattr) {
        this.extattr = extattr;
    }
}
