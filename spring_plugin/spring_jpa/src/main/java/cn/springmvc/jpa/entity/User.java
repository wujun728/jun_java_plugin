package cn.springmvc.jpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Vincent.wang
 *
 */
@Entity
@Table(name = "t_sys_user")
public class User implements BaseEntity<String> {

    private static final long serialVersionUID = 1227495748732942139L;

    private String id;

    /** email **/
    private String email;

    /** 用户名 **/
    private String name;

    /** 真实名称 **/
    private String trueName;

    /** 密码 **/
    private String password;

    /** salt码 **/
    private String salt;

    /** 状态 **/
    private Integer status;

    /** 组织ID **/
    private String organizeId;

    /** 创建时间 **/
    private Date createTime;

    /** 最后登录时间 **/
    private Date lastLoginTime;

    /** 用户最后修改时间 **/
    private Date modifyTime;

    @Id
    @Column(name = "ID", length = 32)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "EMAIL", unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "NAME", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "TRUE_NAME", unique = true)
    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "SALT")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "ORGANIZE_ID")
    public String getOrganizeId() {
        return organizeId;
    }

    public void setOrganizeId(String organizeID) {
        this.organizeId = organizeID;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "LAST_LOGIN_TIME")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Column(name = "MODIFY_TIME")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

}
