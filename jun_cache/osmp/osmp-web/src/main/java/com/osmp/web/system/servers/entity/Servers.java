package com.osmp.web.system.servers.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: 服务器管理
 *
 * @author: wangkaiping
 * @date: 2014年11月27日 上午9:37:47
 */
@Table(name = "t_servers")
public class Servers implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4514254023475919406L;

    /**
     * 状态常量 正常
     */
    public static final int STATE_UP = 1;

    /**
     * 状态常量 不正常
     */
    public static final int STATE_DOWN = 2;

    @Id
    @Column
    private String id;

    /**
     * 服务器名称
     */
    @Column(name = "SERVER_NAME")
    private String serverName;

    /**
     * IP地址
     */
    @Column(name = "SERVER_IP")
    private String serverIp;

    /**
     * 开放端口
     */
    @Column(name = "SERVER_PORT")
    private String serverPort;
    
    /**
     * SSH登陆端口
     */
    @Column(name = "SSH_PORT")
    private String sshPort;

    /**
     * 用户名称
     */
    @Column
    private String user;

    /**
     * 密码
     */
    @Column
    private String password;

    /**
     * 管理界面URL
     */
    @Column(name = "MANAGE_URL")
    private String manageUrl;

    /**
     * 服务器脚本路径
     */
    @Column(name = "COMMAND_PATH")
    private String commandPath;

    /**
     * 状态
     */
    @Column
    private int state;

    /**
     * 备注
     */
    @Column
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getSshPort() {
        return sshPort;
    }

    public void setSshPort(String sshPort) {
        this.sshPort = sshPort;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getManageUrl() {
        return manageUrl;
    }

    public void setManageUrl(String manageUrl) {
        this.manageUrl = manageUrl;
    }

    public String getCommandPath() {
        return commandPath;
    }

    public void setCommandPath(String commandPath) {
        this.commandPath = commandPath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static int getStateUp() {
        return STATE_UP;
    }

    public static int getStateDown() {
        return STATE_DOWN;
    }

    @Override
    public String toString() {
        return "Servers [id=" + id + ", serverName=" + serverName + ", serverIp=" + serverIp + ", sshPort=" + sshPort
                + ", user=" + user + ", password=" + password + ", manageUrl=" + manageUrl + ", commandPath="
                + commandPath + ", state=" + state + ", remark=" + remark + "]";
    }

}
