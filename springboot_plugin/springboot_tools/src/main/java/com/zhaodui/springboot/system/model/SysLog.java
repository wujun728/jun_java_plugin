package com.zhaodui.springboot.system.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_log")
public class SysLog {
    @Id
    private String id;

    /**
     * 日志类型（1登录日志，2操作日志）
     */
    @Column(name = "log_type")
    private Integer logType;

    /**
     * 日志内容
     */
    @Column(name = "log_content")
    private String logContent;

    /**
     * 操作类型
     */
    @Column(name = "operate_type")
    private Integer operateType;

    /**
     * 操作用户账号
     */
    private String userid;

    /**
     * 操作用户名称
     */
    private String username;

    /**
     * IP
     */
    private String ip;

    /**
     * 请求java方法
     */
    private String method;

    /**
     * 请求路径
     */
    @Column(name = "request_url")
    private String requestUrl;

    /**
     * 请求类型
     */
    @Column(name = "request_type")
    private String requestType;

    /**
     * 耗时
     */
    @Column(name = "cost_time")
    private Long costTime;

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
     * 请求参数
     */
    @Column(name = "request_param")
    private String requestParam;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取日志类型（1登录日志，2操作日志）
     *
     * @return log_type - 日志类型（1登录日志，2操作日志）
     */
    public Integer getLogType() {
        return logType;
    }

    /**
     * 设置日志类型（1登录日志，2操作日志）
     *
     * @param logType 日志类型（1登录日志，2操作日志）
     */
    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    /**
     * 获取日志内容
     *
     * @return log_content - 日志内容
     */
    public String getLogContent() {
        return logContent;
    }

    /**
     * 设置日志内容
     *
     * @param logContent 日志内容
     */
    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    /**
     * 获取操作类型
     *
     * @return operate_type - 操作类型
     */
    public Integer getOperateType() {
        return operateType;
    }

    /**
     * 设置操作类型
     *
     * @param operateType 操作类型
     */
    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    /**
     * 获取操作用户账号
     *
     * @return userid - 操作用户账号
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置操作用户账号
     *
     * @param userid 操作用户账号
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取操作用户名称
     *
     * @return username - 操作用户名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置操作用户名称
     *
     * @param username 操作用户名称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取IP
     *
     * @return ip - IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置IP
     *
     * @param ip IP
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 获取请求java方法
     *
     * @return method - 请求java方法
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置请求java方法
     *
     * @param method 请求java方法
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取请求路径
     *
     * @return request_url - 请求路径
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * 设置请求路径
     *
     * @param requestUrl 请求路径
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     * 获取请求类型
     *
     * @return request_type - 请求类型
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * 设置请求类型
     *
     * @param requestType 请求类型
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     * 获取耗时
     *
     * @return cost_time - 耗时
     */
    public Long getCostTime() {
        return costTime;
    }

    /**
     * 设置耗时
     *
     * @param costTime 耗时
     */
    public void setCostTime(Long costTime) {
        this.costTime = costTime;
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

    /**
     * 获取请求参数
     *
     * @return request_param - 请求参数
     */
    public String getRequestParam() {
        return requestParam;
    }

    /**
     * 设置请求参数
     *
     * @param requestParam 请求参数
     */
    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }
}