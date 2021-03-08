package com.zhaodui.springboot.system.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_depart")
public class SysDepart {
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 父机构ID
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 机构/部门名称
     */
    @Column(name = "depart_name")
    private String departName;

    /**
     * 英文名
     */
    @Column(name = "depart_name_en")
    private String departNameEn;

    /**
     * 缩写
     */
    @Column(name = "depart_name_abbr")
    private String departNameAbbr;

    /**
     * 排序
     */
    @Column(name = "depart_order")
    private Integer departOrder;

    /**
     * 机构类别 1组织机构，2岗位
     */
    @Column(name = "org_category")
    private String orgCategory;

    /**
     * 机构类型 1一级部门 2子部门
     */
    @Column(name = "org_type")
    private String orgType;

    /**
     * 机构编码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 传真
     */
    private String fax;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String memo;

    /**
     * 状态（1启用，0不启用）
     */
    private String status;

    /**
     * 删除状态（0，正常，1已删除）
     */
    @Column(name = "del_flag")
    private String delFlag;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新日期
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 描述
     */
    private String description;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取父机构ID
     *
     * @return parent_id - 父机构ID
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置父机构ID
     *
     * @param parentId 父机构ID
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取机构/部门名称
     *
     * @return depart_name - 机构/部门名称
     */
    public String getDepartName() {
        return departName;
    }

    /**
     * 设置机构/部门名称
     *
     * @param departName 机构/部门名称
     */
    public void setDepartName(String departName) {
        this.departName = departName;
    }

    /**
     * 获取英文名
     *
     * @return depart_name_en - 英文名
     */
    public String getDepartNameEn() {
        return departNameEn;
    }

    /**
     * 设置英文名
     *
     * @param departNameEn 英文名
     */
    public void setDepartNameEn(String departNameEn) {
        this.departNameEn = departNameEn;
    }

    /**
     * 获取缩写
     *
     * @return depart_name_abbr - 缩写
     */
    public String getDepartNameAbbr() {
        return departNameAbbr;
    }

    /**
     * 设置缩写
     *
     * @param departNameAbbr 缩写
     */
    public void setDepartNameAbbr(String departNameAbbr) {
        this.departNameAbbr = departNameAbbr;
    }

    /**
     * 获取排序
     *
     * @return depart_order - 排序
     */
    public Integer getDepartOrder() {
        return departOrder;
    }

    /**
     * 设置排序
     *
     * @param departOrder 排序
     */
    public void setDepartOrder(Integer departOrder) {
        this.departOrder = departOrder;
    }

    /**
     * 获取机构类别 1组织机构，2岗位
     *
     * @return org_category - 机构类别 1组织机构，2岗位
     */
    public String getOrgCategory() {
        return orgCategory;
    }

    /**
     * 设置机构类别 1组织机构，2岗位
     *
     * @param orgCategory 机构类别 1组织机构，2岗位
     */
    public void setOrgCategory(String orgCategory) {
        this.orgCategory = orgCategory;
    }

    /**
     * 获取机构类型 1一级部门 2子部门
     *
     * @return org_type - 机构类型 1一级部门 2子部门
     */
    public String getOrgType() {
        return orgType;
    }

    /**
     * 设置机构类型 1一级部门 2子部门
     *
     * @param orgType 机构类型 1一级部门 2子部门
     */
    public void setOrgType(String orgType) {
        this.orgType = orgType;
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
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取传真
     *
     * @return fax - 传真
     */
    public String getFax() {
        return fax;
    }

    /**
     * 设置传真
     *
     * @param fax 传真
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取备注
     *
     * @return memo - 备注
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置备注
     *
     * @param memo 备注
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取状态（1启用，0不启用）
     *
     * @return status - 状态（1启用，0不启用）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态（1启用，0不启用）
     *
     * @param status 状态（1启用，0不启用）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取删除状态（0，正常，1已删除）
     *
     * @return del_flag - 删除状态（0，正常，1已删除）
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 设置删除状态（0，正常，1已删除）
     *
     * @param delFlag 删除状态（0，正常，1已删除）
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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
     * 获取创建日期
     *
     * @return create_time - 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
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
     * 获取更新日期
     *
     * @return update_time - 更新日期
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新日期
     *
     * @param updateTime 更新日期
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }
}