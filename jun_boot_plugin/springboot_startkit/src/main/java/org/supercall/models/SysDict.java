package org.supercall.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class SysDict {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.id
     *
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.dictkey
     *
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    private String dictkey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.dictvalues
     *
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    private String dictvalues;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.dictgroup
     *
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    private String dictgroup;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.createtime
     *
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    private Date createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.creator
     *
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.updatetime
     *
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    private Date updatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_dict.updator
     *
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    private String updator;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.id
     *
     * @return the value of sys_dict.id
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.id
     *
     * @param id the value for sys_dict.id
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.dictkey
     *
     * @return the value of sys_dict.dictkey
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public String getDictkey() {
        return dictkey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.dictkey
     *
     * @param dictkey the value for sys_dict.dictkey
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public void setDictkey(String dictkey) {
        this.dictkey = dictkey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.dictvalues
     *
     * @return the value of sys_dict.dictvalues
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public String getDictvalues() {
        return dictvalues;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.dictvalues
     *
     * @param dictvalues the value for sys_dict.dictvalues
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public void setDictvalues(String dictvalues) {
        this.dictvalues = dictvalues;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.dictgroup
     *
     * @return the value of sys_dict.dictgroup
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public String getDictgroup() {
        return dictgroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.dictgroup
     *
     * @param dictgroup the value for sys_dict.dictgroup
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public void setDictgroup(String dictgroup) {
        this.dictgroup = dictgroup;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.createtime
     *
     * @return the value of sys_dict.createtime
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.createtime
     *
     * @param createtime the value for sys_dict.createtime
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.creator
     *
     * @return the value of sys_dict.creator
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.creator
     *
     * @param creator the value for sys_dict.creator
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.updatetime
     *
     * @return the value of sys_dict.updatetime
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public Date getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.updatetime
     *
     * @param updatetime the value for sys_dict.updatetime
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_dict.updator
     *
     * @return the value of sys_dict.updator
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_dict.updator
     *
     * @param updator the value for sys_dict.updator
     * @mbggenerated Mon Jul 25 21:37:06 CST 2016
     */
    public void setUpdator(String updator) {
        this.updator = updator;
    }
}