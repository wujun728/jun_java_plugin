package com.zhaodui.springboot.system.model;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private String id;

    /**
     * 登录账号
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 密码
     */
    private String password;

    /**
     * md5密码盐
     */
    private String salt;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别(0-默认未知,1-男,2-女)
     */
    private Boolean sex;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 机构编码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 性别(1-正常,2-冻结)
     */
    private Boolean status;

    /**
     * 删除状态(0-正常,1-已删除)
     */
    @Column(name = "del_flag")
    private Boolean delFlag;

    /**
     * 同步工作流引擎(1-同步,0-不同步)
     */
    @Column(name = "activiti_sync")
    private Boolean activitiSync;

    /**
     * 工号，唯一键
     */
    @Column(name = "work_no")
    private String workNo;

    /**
     * 职务，关联职务表
     */
    private String post;

    /**
     * 座机号
     */
    private String telephone;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取登录账号
     *
     * @return username - 登录账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置登录账号
     *
     * @param username 登录账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取真实姓名
     *
     * @return realname - 真实姓名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * 设置真实姓名
     *
     * @param realname 真实姓名
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取md5密码盐
     *
     * @return salt - md5密码盐
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置md5密码盐
     *
     * @param salt md5密码盐
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取头像
     *
     * @return avatar - 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像
     *
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取性别(0-默认未知,1-男,2-女)
     *
     * @return sex - 性别(0-默认未知,1-男,2-女)
     */
    public Boolean getSex() {
        return sex;
    }

    /**
     * 设置性别(0-默认未知,1-男,2-女)
     *
     * @param sex 性别(0-默认未知,1-男,2-女)
     */
    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    /**
     * 获取电子邮件
     *
     * @return email - 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮件
     *
     * @param email 电子邮件
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取机构编码
     *
     * @return org_code - 机构编码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * 设置机构编码
     *
     * @param orgCode 机构编码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取性别(1-正常,2-冻结)
     *
     * @return status - 性别(1-正常,2-冻结)
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置性别(1-正常,2-冻结)
     *
     * @param status 性别(1-正常,2-冻结)
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 获取删除状态(0-正常,1-已删除)
     *
     * @return del_flag - 删除状态(0-正常,1-已删除)
     */
    public Boolean getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除状态(0-正常,1-已删除)
     *
     * @param delFlag 删除状态(0-正常,1-已删除)
     */
    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取同步工作流引擎(1-同步,0-不同步)
     *
     * @return activiti_sync - 同步工作流引擎(1-同步,0-不同步)
     */
    public Boolean getActivitiSync() {
        return activitiSync;
    }

    /**
     * 设置同步工作流引擎(1-同步,0-不同步)
     *
     * @param activitiSync 同步工作流引擎(1-同步,0-不同步)
     */
    public void setActivitiSync(Boolean activitiSync) {
        this.activitiSync = activitiSync;
    }

    /**
     * 获取工号，唯一键
     *
     * @return work_no - 工号，唯一键
     */
    public String getWorkNo() {
        return workNo;
    }

    /**
     * 设置工号，唯一键
     *
     * @param workNo 工号，唯一键
     */
    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    /**
     * 获取职务，关联职务表
     *
     * @return post - 职务，关联职务表
     */
    public String getPost() {
        return post;
    }

    /**
     * 设置职务，关联职务表
     *
     * @param post 职务，关联职务表
     */
    public void setPost(String post) {
        this.post = post;
    }

    /**
     * 获取座机号
     *
     * @return telephone - 座机号
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置座机号
     *
     * @param telephone 座机号
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新人
     *
     * @return update_by - 更新人
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新人
     *
     * @param updateBy 更新人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}