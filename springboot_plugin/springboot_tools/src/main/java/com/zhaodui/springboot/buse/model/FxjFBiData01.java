package com.zhaodui.springboot.buse.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "fxjbi_data_01")
public class FxjFBiData01 {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 创建人名称
     */
    @Column(name = "create_name")
    private String createName;

    /**
     * 创建人登录名称
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建日期
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 更新人名称
     */
    @Column(name = "update_name")
    private String updateName;

    /**
     * 更新人登录名称
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 更新日期
     */
    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 所属部门
     */
    @Column(name = "sys_org_code")
    private String sysOrgCode;

    /**
     * 所属公司
     */
    @Column(name = "sys_company_code")
    private String sysCompanyCode;

    /**
     * 流程状态
     */
    @Column(name = "bpm_status")
    private String bpmStatus;

    /**
     * 筛选条件1
     */
    @Column(name = "fxj_out_01")
    private String fxjOut01;

    /**
     * 筛选条件2
     */
    @Column(name = "fxj_out_02")
    private String fxjOut02;

    /**
     * 筛选条件3
     */
    @Column(name = "fxj_out_03")
    private String fxjOut03;

    /**
     * 筛选条件4
     */
    @Column(name = "fxj_out_04")
    private String fxjOut04;

    /**
     * 筛选条件5
     */
    @Column(name = "fxj_out_05")
    private String fxjOut05;

    /**
     * 筛选条件6
     */
    @Column(name = "fxj_out_06")
    private String fxjOut06;

    /**
     * 筛选条件7
     */
    @Column(name = "fxj_out_07")
    private String fxjOut07;

    /**
     * 筛选条件8
     */
    @Column(name = "fxj_out_08")
    private String fxjOut08;

    /**
     * 筛选条件9
     */
    @Column(name = "fxj_out_09")
    private String fxjOut09;

    /**
     * 筛选条件10
     */
    @Column(name = "fxj_out_10")
    private String fxjOut10;

    /**
     * 无筛选条件11
     */
    @Column(name = "fxj_out_11")
    private String fxjOut11;

    /**
     * 无筛选条件12
     */
    @Column(name = "fxj_out_12")
    private String fxjOut12;

    /**
     * 无筛选条件13
     */
    @Column(name = "fxj_out_13")
    private String fxjOut13;

    /**
     * 无筛选条件14
     */
    @Column(name = "fxj_out_14")
    private String fxjOut14;

    /**
     * 无筛选条件15
     */
    @Column(name = "fxj_out_15")
    private String fxjOut15;

    /**
     * 无筛选条件16
     */
    @Column(name = "fxj_out_16")
    private String fxjOut16;

    /**
     * 无筛选条件17
     */
    @Column(name = "fxj_out_17")
    private String fxjOut17;

    /**
     * 无筛选条件18
     */
    @Column(name = "fxj_out_18")
    private String fxjOut18;

    /**
     * 无筛选条件19
     */
    @Column(name = "fxj_out_19")
    private String fxjOut19;

    /**
     * 无筛选条件20
     */
    @Column(name = "fxj_out_20")
    private String fxjOut20;

    /**
     * 无筛选条件62
     */
    @Column(name = "fxj_out_62")
    private String fxjOut62;

    /**
     * 无筛选条件63
     */
    @Column(name = "fxj_out_63")
    private String fxjOut63;

    /**
     * 无筛选条件64
     */
    @Column(name = "fxj_out_64")
    private String fxjOut64;

    /**
     * 无筛选条件22
     */
    @Column(name = "fxj_out_21")
    private String fxjOut21;

    /**
     * 无筛选条件65
     */
    @Column(name = "fxj_out_65")
    private String fxjOut65;

    /**
     * 无筛选条件60
     */
    @Column(name = "fxj_out_60")
    private String fxjOut60;

    /**
     * 无筛选条件61
     */
    @Column(name = "fxj_out_61")
    private String fxjOut61;

    /**
     * 无筛选条件26
     */
    @Column(name = "fxj_out_26")
    private String fxjOut26;

    /**
     * 无筛选条件27
     */
    @Column(name = "fxj_out_27")
    private String fxjOut27;

    /**
     * 无筛选条件28
     */
    @Column(name = "fxj_out_28")
    private String fxjOut28;

    /**
     * 无筛选条件29
     */
    @Column(name = "fxj_out_29")
    private String fxjOut29;

    /**
     * 无筛选条件21
     */
    @Column(name = "fxj_out_22")
    private String fxjOut22;

    /**
     * 无筛选条件66
     */
    @Column(name = "fxj_out_66")
    private String fxjOut66;

    /**
     * 无筛选条件23
     */
    @Column(name = "fxj_out_23")
    private String fxjOut23;

    /**
     * 无筛选条件67
     */
    @Column(name = "fxj_out_67")
    private String fxjOut67;

    /**
     * 无筛选条件24
     */
    @Column(name = "fxj_out_24")
    private String fxjOut24;

    /**
     * 无筛选条件68
     */
    @Column(name = "fxj_out_68")
    private String fxjOut68;

    /**
     * 无筛选条件25
     */
    @Column(name = "fxj_out_25")
    private String fxjOut25;

    /**
     * 无筛选条件69
     */
    @Column(name = "fxj_out_69")
    private String fxjOut69;

    /**
     * 无筛选条件73
     */
    @Column(name = "fxj_out_73")
    private String fxjOut73;

    /**
     * 无筛选条件30
     */
    @Column(name = "fxj_out_30")
    private String fxjOut30;

    /**
     * 无筛选条件74
     */
    @Column(name = "fxj_out_74")
    private String fxjOut74;

    /**
     * 无筛选条件31
     */
    @Column(name = "fxj_out_31")
    private String fxjOut31;

    /**
     * 无筛选条件75
     */
    @Column(name = "fxj_out_75")
    private String fxjOut75;

    /**
     * 无筛选条件32
     */
    @Column(name = "fxj_out_32")
    private String fxjOut32;

    /**
     * 无筛选条件76
     */
    @Column(name = "fxj_out_76")
    private String fxjOut76;

    /**
     * 无筛选条件70
     */
    @Column(name = "fxj_out_70")
    private String fxjOut70;

    /**
     * 无筛选条件71
     */
    @Column(name = "fxj_out_71")
    private String fxjOut71;

    /**
     * 无筛选条件72
     */
    @Column(name = "fxj_out_72")
    private String fxjOut72;

    /**
     * 无筛选条件37
     */
    @Column(name = "fxj_out_37")
    private String fxjOut37;

    /**
     * 无筛选条件38
     */
    @Column(name = "fxj_out_38")
    private String fxjOut38;

    /**
     * 无筛选条件39
     */
    @Column(name = "fxj_out_39")
    private String fxjOut39;

    /**
     * 无筛选条件33
     */
    @Column(name = "fxj_out_33")
    private String fxjOut33;

    /**
     * 无筛选条件77
     */
    @Column(name = "fxj_out_77")
    private String fxjOut77;

    /**
     * 无筛选条件34
     */
    @Column(name = "fxj_out_34")
    private String fxjOut34;

    /**
     * 无筛选条件78
     */
    @Column(name = "fxj_out_78")
    private String fxjOut78;

    /**
     * 无筛选条件35
     */
    @Column(name = "fxj_out_35")
    private String fxjOut35;

    /**
     * 无筛选条件79
     */
    @Column(name = "fxj_out_79")
    private String fxjOut79;

    /**
     * 无筛选条件36
     */
    @Column(name = "fxj_out_36")
    private String fxjOut36;

    /**
     * 无筛选条件40
     */
    @Column(name = "fxj_out_40")
    private String fxjOut40;

    /**
     * 无筛选条件41
     */
    @Column(name = "fxj_out_41")
    private String fxjOut41;

    /**
     * 无筛选条件42
     */
    @Column(name = "fxj_out_42")
    private String fxjOut42;

    /**
     * 无筛选条件43
     */
    @Column(name = "fxj_out_43")
    private String fxjOut43;

    /**
     * 无筛选条件80
     */
    @Column(name = "fxj_out_80")
    private String fxjOut80;

    /**
     * 无筛选条件48
     */
    @Column(name = "fxj_out_48")
    private String fxjOut48;

    /**
     * 无筛选条件49
     */
    @Column(name = "fxj_out_49")
    private String fxjOut49;

    /**
     * 无筛选条件44
     */
    @Column(name = "fxj_out_44")
    private String fxjOut44;

    /**
     * 无筛选条件45
     */
    @Column(name = "fxj_out_45")
    private String fxjOut45;

    /**
     * 无筛选条件46
     */
    @Column(name = "fxj_out_46")
    private String fxjOut46;

    /**
     * 无筛选条件47
     */
    @Column(name = "fxj_out_47")
    private String fxjOut47;

    /**
     * 无筛选条件51
     */
    @Column(name = "fxj_out_51")
    private String fxjOut51;

    /**
     * 无筛选条件52
     */
    @Column(name = "fxj_out_52")
    private String fxjOut52;

    /**
     * 无筛选条件53
     */
    @Column(name = "fxj_out_53")
    private String fxjOut53;

    /**
     * 无筛选条件54
     */
    @Column(name = "fxj_out_54")
    private String fxjOut54;

    /**
     * 无筛选条件50
     */
    @Column(name = "fxj_out_50")
    private String fxjOut50;

    /**
     * 无筛选条件59
     */
    @Column(name = "fxj_out_59")
    private String fxjOut59;

    /**
     * 无筛选条件55
     */
    @Column(name = "fxj_out_55")
    private String fxjOut55;

    /**
     * 无筛选条件56
     */
    @Column(name = "fxj_out_56")
    private String fxjOut56;

    /**
     * 无筛选条件57
     */
    @Column(name = "fxj_out_57")
    private String fxjOut57;

    /**
     * 无筛选条件58
     */
    @Column(name = "fxj_out_58")
    private String fxjOut58;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取创建人名称
     *
     * @return create_name - 创建人名称
     */
    public String getCreateName() {
        return createName;
    }

    /**
     * 设置创建人名称
     *
     * @param createName 创建人名称
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 获取创建人登录名称
     *
     * @return create_by - 创建人登录名称
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人登录名称
     *
     * @param createBy 创建人登录名称
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建日期
     *
     * @return create_date - 创建日期
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建日期
     *
     * @param createDate 创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取更新人名称
     *
     * @return update_name - 更新人名称
     */
    public String getUpdateName() {
        return updateName;
    }

    /**
     * 设置更新人名称
     *
     * @param updateName 更新人名称
     */
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    /**
     * 获取更新人登录名称
     *
     * @return update_by - 更新人登录名称
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置更新人登录名称
     *
     * @param updateBy 更新人登录名称
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取更新日期
     *
     * @return update_date - 更新日期
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置更新日期
     *
     * @param updateDate 更新日期
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取所属部门
     *
     * @return sys_org_code - 所属部门
     */
    public String getSysOrgCode() {
        return sysOrgCode;
    }

    /**
     * 设置所属部门
     *
     * @param sysOrgCode 所属部门
     */
    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    /**
     * 获取所属公司
     *
     * @return sys_company_code - 所属公司
     */
    public String getSysCompanyCode() {
        return sysCompanyCode;
    }

    /**
     * 设置所属公司
     *
     * @param sysCompanyCode 所属公司
     */
    public void setSysCompanyCode(String sysCompanyCode) {
        this.sysCompanyCode = sysCompanyCode;
    }

    /**
     * 获取流程状态
     *
     * @return bpm_status - 流程状态
     */
    public String getBpmStatus() {
        return bpmStatus;
    }

    /**
     * 设置流程状态
     *
     * @param bpmStatus 流程状态
     */
    public void setBpmStatus(String bpmStatus) {
        this.bpmStatus = bpmStatus;
    }

    /**
     * 获取筛选条件1
     *
     * @return fxj_out_01 - 筛选条件1
     */
    public String getFxjOut01() {
        return fxjOut01;
    }

    /**
     * 设置筛选条件1
     *
     * @param fxjOut01 筛选条件1
     */
    public void setFxjOut01(String fxjOut01) {
        this.fxjOut01 = fxjOut01;
    }

    /**
     * 获取筛选条件2
     *
     * @return fxj_out_02 - 筛选条件2
     */
    public String getFxjOut02() {
        return fxjOut02;
    }

    /**
     * 设置筛选条件2
     *
     * @param fxjOut02 筛选条件2
     */
    public void setFxjOut02(String fxjOut02) {
        this.fxjOut02 = fxjOut02;
    }

    /**
     * 获取筛选条件3
     *
     * @return fxj_out_03 - 筛选条件3
     */
    public String getFxjOut03() {
        return fxjOut03;
    }

    /**
     * 设置筛选条件3
     *
     * @param fxjOut03 筛选条件3
     */
    public void setFxjOut03(String fxjOut03) {
        this.fxjOut03 = fxjOut03;
    }

    /**
     * 获取筛选条件4
     *
     * @return fxj_out_04 - 筛选条件4
     */
    public String getFxjOut04() {
        return fxjOut04;
    }

    /**
     * 设置筛选条件4
     *
     * @param fxjOut04 筛选条件4
     */
    public void setFxjOut04(String fxjOut04) {
        this.fxjOut04 = fxjOut04;
    }

    /**
     * 获取筛选条件5
     *
     * @return fxj_out_05 - 筛选条件5
     */
    public String getFxjOut05() {
        return fxjOut05;
    }

    /**
     * 设置筛选条件5
     *
     * @param fxjOut05 筛选条件5
     */
    public void setFxjOut05(String fxjOut05) {
        this.fxjOut05 = fxjOut05;
    }

    /**
     * 获取筛选条件6
     *
     * @return fxj_out_06 - 筛选条件6
     */
    public String getFxjOut06() {
        return fxjOut06;
    }

    /**
     * 设置筛选条件6
     *
     * @param fxjOut06 筛选条件6
     */
    public void setFxjOut06(String fxjOut06) {
        this.fxjOut06 = fxjOut06;
    }

    /**
     * 获取筛选条件7
     *
     * @return fxj_out_07 - 筛选条件7
     */
    public String getFxjOut07() {
        return fxjOut07;
    }

    /**
     * 设置筛选条件7
     *
     * @param fxjOut07 筛选条件7
     */
    public void setFxjOut07(String fxjOut07) {
        this.fxjOut07 = fxjOut07;
    }

    /**
     * 获取筛选条件8
     *
     * @return fxj_out_08 - 筛选条件8
     */
    public String getFxjOut08() {
        return fxjOut08;
    }

    /**
     * 设置筛选条件8
     *
     * @param fxjOut08 筛选条件8
     */
    public void setFxjOut08(String fxjOut08) {
        this.fxjOut08 = fxjOut08;
    }

    /**
     * 获取筛选条件9
     *
     * @return fxj_out_09 - 筛选条件9
     */
    public String getFxjOut09() {
        return fxjOut09;
    }

    /**
     * 设置筛选条件9
     *
     * @param fxjOut09 筛选条件9
     */
    public void setFxjOut09(String fxjOut09) {
        this.fxjOut09 = fxjOut09;
    }

    /**
     * 获取筛选条件10
     *
     * @return fxj_out_10 - 筛选条件10
     */
    public String getFxjOut10() {
        return fxjOut10;
    }

    /**
     * 设置筛选条件10
     *
     * @param fxjOut10 筛选条件10
     */
    public void setFxjOut10(String fxjOut10) {
        this.fxjOut10 = fxjOut10;
    }

    /**
     * 获取无筛选条件11
     *
     * @return fxj_out_11 - 无筛选条件11
     */
    public String getFxjOut11() {
        return fxjOut11;
    }

    /**
     * 设置无筛选条件11
     *
     * @param fxjOut11 无筛选条件11
     */
    public void setFxjOut11(String fxjOut11) {
        this.fxjOut11 = fxjOut11;
    }

    /**
     * 获取无筛选条件12
     *
     * @return fxj_out_12 - 无筛选条件12
     */
    public String getFxjOut12() {
        return fxjOut12;
    }

    /**
     * 设置无筛选条件12
     *
     * @param fxjOut12 无筛选条件12
     */
    public void setFxjOut12(String fxjOut12) {
        this.fxjOut12 = fxjOut12;
    }

    /**
     * 获取无筛选条件13
     *
     * @return fxj_out_13 - 无筛选条件13
     */
    public String getFxjOut13() {
        return fxjOut13;
    }

    /**
     * 设置无筛选条件13
     *
     * @param fxjOut13 无筛选条件13
     */
    public void setFxjOut13(String fxjOut13) {
        this.fxjOut13 = fxjOut13;
    }

    /**
     * 获取无筛选条件14
     *
     * @return fxj_out_14 - 无筛选条件14
     */
    public String getFxjOut14() {
        return fxjOut14;
    }

    /**
     * 设置无筛选条件14
     *
     * @param fxjOut14 无筛选条件14
     */
    public void setFxjOut14(String fxjOut14) {
        this.fxjOut14 = fxjOut14;
    }

    /**
     * 获取无筛选条件15
     *
     * @return fxj_out_15 - 无筛选条件15
     */
    public String getFxjOut15() {
        return fxjOut15;
    }

    /**
     * 设置无筛选条件15
     *
     * @param fxjOut15 无筛选条件15
     */
    public void setFxjOut15(String fxjOut15) {
        this.fxjOut15 = fxjOut15;
    }

    /**
     * 获取无筛选条件16
     *
     * @return fxj_out_16 - 无筛选条件16
     */
    public String getFxjOut16() {
        return fxjOut16;
    }

    /**
     * 设置无筛选条件16
     *
     * @param fxjOut16 无筛选条件16
     */
    public void setFxjOut16(String fxjOut16) {
        this.fxjOut16 = fxjOut16;
    }

    /**
     * 获取无筛选条件17
     *
     * @return fxj_out_17 - 无筛选条件17
     */
    public String getFxjOut17() {
        return fxjOut17;
    }

    /**
     * 设置无筛选条件17
     *
     * @param fxjOut17 无筛选条件17
     */
    public void setFxjOut17(String fxjOut17) {
        this.fxjOut17 = fxjOut17;
    }

    /**
     * 获取无筛选条件18
     *
     * @return fxj_out_18 - 无筛选条件18
     */
    public String getFxjOut18() {
        return fxjOut18;
    }

    /**
     * 设置无筛选条件18
     *
     * @param fxjOut18 无筛选条件18
     */
    public void setFxjOut18(String fxjOut18) {
        this.fxjOut18 = fxjOut18;
    }

    /**
     * 获取无筛选条件19
     *
     * @return fxj_out_19 - 无筛选条件19
     */
    public String getFxjOut19() {
        return fxjOut19;
    }

    /**
     * 设置无筛选条件19
     *
     * @param fxjOut19 无筛选条件19
     */
    public void setFxjOut19(String fxjOut19) {
        this.fxjOut19 = fxjOut19;
    }

    /**
     * 获取无筛选条件20
     *
     * @return fxj_out_20 - 无筛选条件20
     */
    public String getFxjOut20() {
        return fxjOut20;
    }

    /**
     * 设置无筛选条件20
     *
     * @param fxjOut20 无筛选条件20
     */
    public void setFxjOut20(String fxjOut20) {
        this.fxjOut20 = fxjOut20;
    }

    /**
     * 获取无筛选条件62
     *
     * @return fxj_out_62 - 无筛选条件62
     */
    public String getFxjOut62() {
        return fxjOut62;
    }

    /**
     * 设置无筛选条件62
     *
     * @param fxjOut62 无筛选条件62
     */
    public void setFxjOut62(String fxjOut62) {
        this.fxjOut62 = fxjOut62;
    }

    /**
     * 获取无筛选条件63
     *
     * @return fxj_out_63 - 无筛选条件63
     */
    public String getFxjOut63() {
        return fxjOut63;
    }

    /**
     * 设置无筛选条件63
     *
     * @param fxjOut63 无筛选条件63
     */
    public void setFxjOut63(String fxjOut63) {
        this.fxjOut63 = fxjOut63;
    }

    /**
     * 获取无筛选条件64
     *
     * @return fxj_out_64 - 无筛选条件64
     */
    public String getFxjOut64() {
        return fxjOut64;
    }

    /**
     * 设置无筛选条件64
     *
     * @param fxjOut64 无筛选条件64
     */
    public void setFxjOut64(String fxjOut64) {
        this.fxjOut64 = fxjOut64;
    }

    /**
     * 获取无筛选条件22
     *
     * @return fxj_out_21 - 无筛选条件22
     */
    public String getFxjOut21() {
        return fxjOut21;
    }

    /**
     * 设置无筛选条件22
     *
     * @param fxjOut21 无筛选条件22
     */
    public void setFxjOut21(String fxjOut21) {
        this.fxjOut21 = fxjOut21;
    }

    /**
     * 获取无筛选条件65
     *
     * @return fxj_out_65 - 无筛选条件65
     */
    public String getFxjOut65() {
        return fxjOut65;
    }

    /**
     * 设置无筛选条件65
     *
     * @param fxjOut65 无筛选条件65
     */
    public void setFxjOut65(String fxjOut65) {
        this.fxjOut65 = fxjOut65;
    }

    /**
     * 获取无筛选条件60
     *
     * @return fxj_out_60 - 无筛选条件60
     */
    public String getFxjOut60() {
        return fxjOut60;
    }

    /**
     * 设置无筛选条件60
     *
     * @param fxjOut60 无筛选条件60
     */
    public void setFxjOut60(String fxjOut60) {
        this.fxjOut60 = fxjOut60;
    }

    /**
     * 获取无筛选条件61
     *
     * @return fxj_out_61 - 无筛选条件61
     */
    public String getFxjOut61() {
        return fxjOut61;
    }

    /**
     * 设置无筛选条件61
     *
     * @param fxjOut61 无筛选条件61
     */
    public void setFxjOut61(String fxjOut61) {
        this.fxjOut61 = fxjOut61;
    }

    /**
     * 获取无筛选条件26
     *
     * @return fxj_out_26 - 无筛选条件26
     */
    public String getFxjOut26() {
        return fxjOut26;
    }

    /**
     * 设置无筛选条件26
     *
     * @param fxjOut26 无筛选条件26
     */
    public void setFxjOut26(String fxjOut26) {
        this.fxjOut26 = fxjOut26;
    }

    /**
     * 获取无筛选条件27
     *
     * @return fxj_out_27 - 无筛选条件27
     */
    public String getFxjOut27() {
        return fxjOut27;
    }

    /**
     * 设置无筛选条件27
     *
     * @param fxjOut27 无筛选条件27
     */
    public void setFxjOut27(String fxjOut27) {
        this.fxjOut27 = fxjOut27;
    }

    /**
     * 获取无筛选条件28
     *
     * @return fxj_out_28 - 无筛选条件28
     */
    public String getFxjOut28() {
        return fxjOut28;
    }

    /**
     * 设置无筛选条件28
     *
     * @param fxjOut28 无筛选条件28
     */
    public void setFxjOut28(String fxjOut28) {
        this.fxjOut28 = fxjOut28;
    }

    /**
     * 获取无筛选条件29
     *
     * @return fxj_out_29 - 无筛选条件29
     */
    public String getFxjOut29() {
        return fxjOut29;
    }

    /**
     * 设置无筛选条件29
     *
     * @param fxjOut29 无筛选条件29
     */
    public void setFxjOut29(String fxjOut29) {
        this.fxjOut29 = fxjOut29;
    }

    /**
     * 获取无筛选条件21
     *
     * @return fxj_out_22 - 无筛选条件21
     */
    public String getFxjOut22() {
        return fxjOut22;
    }

    /**
     * 设置无筛选条件21
     *
     * @param fxjOut22 无筛选条件21
     */
    public void setFxjOut22(String fxjOut22) {
        this.fxjOut22 = fxjOut22;
    }

    /**
     * 获取无筛选条件66
     *
     * @return fxj_out_66 - 无筛选条件66
     */
    public String getFxjOut66() {
        return fxjOut66;
    }

    /**
     * 设置无筛选条件66
     *
     * @param fxjOut66 无筛选条件66
     */
    public void setFxjOut66(String fxjOut66) {
        this.fxjOut66 = fxjOut66;
    }

    /**
     * 获取无筛选条件23
     *
     * @return fxj_out_23 - 无筛选条件23
     */
    public String getFxjOut23() {
        return fxjOut23;
    }

    /**
     * 设置无筛选条件23
     *
     * @param fxjOut23 无筛选条件23
     */
    public void setFxjOut23(String fxjOut23) {
        this.fxjOut23 = fxjOut23;
    }

    /**
     * 获取无筛选条件67
     *
     * @return fxj_out_67 - 无筛选条件67
     */
    public String getFxjOut67() {
        return fxjOut67;
    }

    /**
     * 设置无筛选条件67
     *
     * @param fxjOut67 无筛选条件67
     */
    public void setFxjOut67(String fxjOut67) {
        this.fxjOut67 = fxjOut67;
    }

    /**
     * 获取无筛选条件24
     *
     * @return fxj_out_24 - 无筛选条件24
     */
    public String getFxjOut24() {
        return fxjOut24;
    }

    /**
     * 设置无筛选条件24
     *
     * @param fxjOut24 无筛选条件24
     */
    public void setFxjOut24(String fxjOut24) {
        this.fxjOut24 = fxjOut24;
    }

    /**
     * 获取无筛选条件68
     *
     * @return fxj_out_68 - 无筛选条件68
     */
    public String getFxjOut68() {
        return fxjOut68;
    }

    /**
     * 设置无筛选条件68
     *
     * @param fxjOut68 无筛选条件68
     */
    public void setFxjOut68(String fxjOut68) {
        this.fxjOut68 = fxjOut68;
    }

    /**
     * 获取无筛选条件25
     *
     * @return fxj_out_25 - 无筛选条件25
     */
    public String getFxjOut25() {
        return fxjOut25;
    }

    /**
     * 设置无筛选条件25
     *
     * @param fxjOut25 无筛选条件25
     */
    public void setFxjOut25(String fxjOut25) {
        this.fxjOut25 = fxjOut25;
    }

    /**
     * 获取无筛选条件69
     *
     * @return fxj_out_69 - 无筛选条件69
     */
    public String getFxjOut69() {
        return fxjOut69;
    }

    /**
     * 设置无筛选条件69
     *
     * @param fxjOut69 无筛选条件69
     */
    public void setFxjOut69(String fxjOut69) {
        this.fxjOut69 = fxjOut69;
    }

    /**
     * 获取无筛选条件73
     *
     * @return fxj_out_73 - 无筛选条件73
     */
    public String getFxjOut73() {
        return fxjOut73;
    }

    /**
     * 设置无筛选条件73
     *
     * @param fxjOut73 无筛选条件73
     */
    public void setFxjOut73(String fxjOut73) {
        this.fxjOut73 = fxjOut73;
    }

    /**
     * 获取无筛选条件30
     *
     * @return fxj_out_30 - 无筛选条件30
     */
    public String getFxjOut30() {
        return fxjOut30;
    }

    /**
     * 设置无筛选条件30
     *
     * @param fxjOut30 无筛选条件30
     */
    public void setFxjOut30(String fxjOut30) {
        this.fxjOut30 = fxjOut30;
    }

    /**
     * 获取无筛选条件74
     *
     * @return fxj_out_74 - 无筛选条件74
     */
    public String getFxjOut74() {
        return fxjOut74;
    }

    /**
     * 设置无筛选条件74
     *
     * @param fxjOut74 无筛选条件74
     */
    public void setFxjOut74(String fxjOut74) {
        this.fxjOut74 = fxjOut74;
    }

    /**
     * 获取无筛选条件31
     *
     * @return fxj_out_31 - 无筛选条件31
     */
    public String getFxjOut31() {
        return fxjOut31;
    }

    /**
     * 设置无筛选条件31
     *
     * @param fxjOut31 无筛选条件31
     */
    public void setFxjOut31(String fxjOut31) {
        this.fxjOut31 = fxjOut31;
    }

    /**
     * 获取无筛选条件75
     *
     * @return fxj_out_75 - 无筛选条件75
     */
    public String getFxjOut75() {
        return fxjOut75;
    }

    /**
     * 设置无筛选条件75
     *
     * @param fxjOut75 无筛选条件75
     */
    public void setFxjOut75(String fxjOut75) {
        this.fxjOut75 = fxjOut75;
    }

    /**
     * 获取无筛选条件32
     *
     * @return fxj_out_32 - 无筛选条件32
     */
    public String getFxjOut32() {
        return fxjOut32;
    }

    /**
     * 设置无筛选条件32
     *
     * @param fxjOut32 无筛选条件32
     */
    public void setFxjOut32(String fxjOut32) {
        this.fxjOut32 = fxjOut32;
    }

    /**
     * 获取无筛选条件76
     *
     * @return fxj_out_76 - 无筛选条件76
     */
    public String getFxjOut76() {
        return fxjOut76;
    }

    /**
     * 设置无筛选条件76
     *
     * @param fxjOut76 无筛选条件76
     */
    public void setFxjOut76(String fxjOut76) {
        this.fxjOut76 = fxjOut76;
    }

    /**
     * 获取无筛选条件70
     *
     * @return fxj_out_70 - 无筛选条件70
     */
    public String getFxjOut70() {
        return fxjOut70;
    }

    /**
     * 设置无筛选条件70
     *
     * @param fxjOut70 无筛选条件70
     */
    public void setFxjOut70(String fxjOut70) {
        this.fxjOut70 = fxjOut70;
    }

    /**
     * 获取无筛选条件71
     *
     * @return fxj_out_71 - 无筛选条件71
     */
    public String getFxjOut71() {
        return fxjOut71;
    }

    /**
     * 设置无筛选条件71
     *
     * @param fxjOut71 无筛选条件71
     */
    public void setFxjOut71(String fxjOut71) {
        this.fxjOut71 = fxjOut71;
    }

    /**
     * 获取无筛选条件72
     *
     * @return fxj_out_72 - 无筛选条件72
     */
    public String getFxjOut72() {
        return fxjOut72;
    }

    /**
     * 设置无筛选条件72
     *
     * @param fxjOut72 无筛选条件72
     */
    public void setFxjOut72(String fxjOut72) {
        this.fxjOut72 = fxjOut72;
    }

    /**
     * 获取无筛选条件37
     *
     * @return fxj_out_37 - 无筛选条件37
     */
    public String getFxjOut37() {
        return fxjOut37;
    }

    /**
     * 设置无筛选条件37
     *
     * @param fxjOut37 无筛选条件37
     */
    public void setFxjOut37(String fxjOut37) {
        this.fxjOut37 = fxjOut37;
    }

    /**
     * 获取无筛选条件38
     *
     * @return fxj_out_38 - 无筛选条件38
     */
    public String getFxjOut38() {
        return fxjOut38;
    }

    /**
     * 设置无筛选条件38
     *
     * @param fxjOut38 无筛选条件38
     */
    public void setFxjOut38(String fxjOut38) {
        this.fxjOut38 = fxjOut38;
    }

    /**
     * 获取无筛选条件39
     *
     * @return fxj_out_39 - 无筛选条件39
     */
    public String getFxjOut39() {
        return fxjOut39;
    }

    /**
     * 设置无筛选条件39
     *
     * @param fxjOut39 无筛选条件39
     */
    public void setFxjOut39(String fxjOut39) {
        this.fxjOut39 = fxjOut39;
    }

    /**
     * 获取无筛选条件33
     *
     * @return fxj_out_33 - 无筛选条件33
     */
    public String getFxjOut33() {
        return fxjOut33;
    }

    /**
     * 设置无筛选条件33
     *
     * @param fxjOut33 无筛选条件33
     */
    public void setFxjOut33(String fxjOut33) {
        this.fxjOut33 = fxjOut33;
    }

    /**
     * 获取无筛选条件77
     *
     * @return fxj_out_77 - 无筛选条件77
     */
    public String getFxjOut77() {
        return fxjOut77;
    }

    /**
     * 设置无筛选条件77
     *
     * @param fxjOut77 无筛选条件77
     */
    public void setFxjOut77(String fxjOut77) {
        this.fxjOut77 = fxjOut77;
    }

    /**
     * 获取无筛选条件34
     *
     * @return fxj_out_34 - 无筛选条件34
     */
    public String getFxjOut34() {
        return fxjOut34;
    }

    /**
     * 设置无筛选条件34
     *
     * @param fxjOut34 无筛选条件34
     */
    public void setFxjOut34(String fxjOut34) {
        this.fxjOut34 = fxjOut34;
    }

    /**
     * 获取无筛选条件78
     *
     * @return fxj_out_78 - 无筛选条件78
     */
    public String getFxjOut78() {
        return fxjOut78;
    }

    /**
     * 设置无筛选条件78
     *
     * @param fxjOut78 无筛选条件78
     */
    public void setFxjOut78(String fxjOut78) {
        this.fxjOut78 = fxjOut78;
    }

    /**
     * 获取无筛选条件35
     *
     * @return fxj_out_35 - 无筛选条件35
     */
    public String getFxjOut35() {
        return fxjOut35;
    }

    /**
     * 设置无筛选条件35
     *
     * @param fxjOut35 无筛选条件35
     */
    public void setFxjOut35(String fxjOut35) {
        this.fxjOut35 = fxjOut35;
    }

    /**
     * 获取无筛选条件79
     *
     * @return fxj_out_79 - 无筛选条件79
     */
    public String getFxjOut79() {
        return fxjOut79;
    }

    /**
     * 设置无筛选条件79
     *
     * @param fxjOut79 无筛选条件79
     */
    public void setFxjOut79(String fxjOut79) {
        this.fxjOut79 = fxjOut79;
    }

    /**
     * 获取无筛选条件36
     *
     * @return fxj_out_36 - 无筛选条件36
     */
    public String getFxjOut36() {
        return fxjOut36;
    }

    /**
     * 设置无筛选条件36
     *
     * @param fxjOut36 无筛选条件36
     */
    public void setFxjOut36(String fxjOut36) {
        this.fxjOut36 = fxjOut36;
    }

    /**
     * 获取无筛选条件40
     *
     * @return fxj_out_40 - 无筛选条件40
     */
    public String getFxjOut40() {
        return fxjOut40;
    }

    /**
     * 设置无筛选条件40
     *
     * @param fxjOut40 无筛选条件40
     */
    public void setFxjOut40(String fxjOut40) {
        this.fxjOut40 = fxjOut40;
    }

    /**
     * 获取无筛选条件41
     *
     * @return fxj_out_41 - 无筛选条件41
     */
    public String getFxjOut41() {
        return fxjOut41;
    }

    /**
     * 设置无筛选条件41
     *
     * @param fxjOut41 无筛选条件41
     */
    public void setFxjOut41(String fxjOut41) {
        this.fxjOut41 = fxjOut41;
    }

    /**
     * 获取无筛选条件42
     *
     * @return fxj_out_42 - 无筛选条件42
     */
    public String getFxjOut42() {
        return fxjOut42;
    }

    /**
     * 设置无筛选条件42
     *
     * @param fxjOut42 无筛选条件42
     */
    public void setFxjOut42(String fxjOut42) {
        this.fxjOut42 = fxjOut42;
    }

    /**
     * 获取无筛选条件43
     *
     * @return fxj_out_43 - 无筛选条件43
     */
    public String getFxjOut43() {
        return fxjOut43;
    }

    /**
     * 设置无筛选条件43
     *
     * @param fxjOut43 无筛选条件43
     */
    public void setFxjOut43(String fxjOut43) {
        this.fxjOut43 = fxjOut43;
    }

    /**
     * 获取无筛选条件80
     *
     * @return fxj_out_80 - 无筛选条件80
     */
    public String getFxjOut80() {
        return fxjOut80;
    }

    /**
     * 设置无筛选条件80
     *
     * @param fxjOut80 无筛选条件80
     */
    public void setFxjOut80(String fxjOut80) {
        this.fxjOut80 = fxjOut80;
    }

    /**
     * 获取无筛选条件48
     *
     * @return fxj_out_48 - 无筛选条件48
     */
    public String getFxjOut48() {
        return fxjOut48;
    }

    /**
     * 设置无筛选条件48
     *
     * @param fxjOut48 无筛选条件48
     */
    public void setFxjOut48(String fxjOut48) {
        this.fxjOut48 = fxjOut48;
    }

    /**
     * 获取无筛选条件49
     *
     * @return fxj_out_49 - 无筛选条件49
     */
    public String getFxjOut49() {
        return fxjOut49;
    }

    /**
     * 设置无筛选条件49
     *
     * @param fxjOut49 无筛选条件49
     */
    public void setFxjOut49(String fxjOut49) {
        this.fxjOut49 = fxjOut49;
    }

    /**
     * 获取无筛选条件44
     *
     * @return fxj_out_44 - 无筛选条件44
     */
    public String getFxjOut44() {
        return fxjOut44;
    }

    /**
     * 设置无筛选条件44
     *
     * @param fxjOut44 无筛选条件44
     */
    public void setFxjOut44(String fxjOut44) {
        this.fxjOut44 = fxjOut44;
    }

    /**
     * 获取无筛选条件45
     *
     * @return fxj_out_45 - 无筛选条件45
     */
    public String getFxjOut45() {
        return fxjOut45;
    }

    /**
     * 设置无筛选条件45
     *
     * @param fxjOut45 无筛选条件45
     */
    public void setFxjOut45(String fxjOut45) {
        this.fxjOut45 = fxjOut45;
    }

    /**
     * 获取无筛选条件46
     *
     * @return fxj_out_46 - 无筛选条件46
     */
    public String getFxjOut46() {
        return fxjOut46;
    }

    /**
     * 设置无筛选条件46
     *
     * @param fxjOut46 无筛选条件46
     */
    public void setFxjOut46(String fxjOut46) {
        this.fxjOut46 = fxjOut46;
    }

    /**
     * 获取无筛选条件47
     *
     * @return fxj_out_47 - 无筛选条件47
     */
    public String getFxjOut47() {
        return fxjOut47;
    }

    /**
     * 设置无筛选条件47
     *
     * @param fxjOut47 无筛选条件47
     */
    public void setFxjOut47(String fxjOut47) {
        this.fxjOut47 = fxjOut47;
    }

    /**
     * 获取无筛选条件51
     *
     * @return fxj_out_51 - 无筛选条件51
     */
    public String getFxjOut51() {
        return fxjOut51;
    }

    /**
     * 设置无筛选条件51
     *
     * @param fxjOut51 无筛选条件51
     */
    public void setFxjOut51(String fxjOut51) {
        this.fxjOut51 = fxjOut51;
    }

    /**
     * 获取无筛选条件52
     *
     * @return fxj_out_52 - 无筛选条件52
     */
    public String getFxjOut52() {
        return fxjOut52;
    }

    /**
     * 设置无筛选条件52
     *
     * @param fxjOut52 无筛选条件52
     */
    public void setFxjOut52(String fxjOut52) {
        this.fxjOut52 = fxjOut52;
    }

    /**
     * 获取无筛选条件53
     *
     * @return fxj_out_53 - 无筛选条件53
     */
    public String getFxjOut53() {
        return fxjOut53;
    }

    /**
     * 设置无筛选条件53
     *
     * @param fxjOut53 无筛选条件53
     */
    public void setFxjOut53(String fxjOut53) {
        this.fxjOut53 = fxjOut53;
    }

    /**
     * 获取无筛选条件54
     *
     * @return fxj_out_54 - 无筛选条件54
     */
    public String getFxjOut54() {
        return fxjOut54;
    }

    /**
     * 设置无筛选条件54
     *
     * @param fxjOut54 无筛选条件54
     */
    public void setFxjOut54(String fxjOut54) {
        this.fxjOut54 = fxjOut54;
    }

    /**
     * 获取无筛选条件50
     *
     * @return fxj_out_50 - 无筛选条件50
     */
    public String getFxjOut50() {
        return fxjOut50;
    }

    /**
     * 设置无筛选条件50
     *
     * @param fxjOut50 无筛选条件50
     */
    public void setFxjOut50(String fxjOut50) {
        this.fxjOut50 = fxjOut50;
    }

    /**
     * 获取无筛选条件59
     *
     * @return fxj_out_59 - 无筛选条件59
     */
    public String getFxjOut59() {
        return fxjOut59;
    }

    /**
     * 设置无筛选条件59
     *
     * @param fxjOut59 无筛选条件59
     */
    public void setFxjOut59(String fxjOut59) {
        this.fxjOut59 = fxjOut59;
    }

    /**
     * 获取无筛选条件55
     *
     * @return fxj_out_55 - 无筛选条件55
     */
    public String getFxjOut55() {
        return fxjOut55;
    }

    /**
     * 设置无筛选条件55
     *
     * @param fxjOut55 无筛选条件55
     */
    public void setFxjOut55(String fxjOut55) {
        this.fxjOut55 = fxjOut55;
    }

    /**
     * 获取无筛选条件56
     *
     * @return fxj_out_56 - 无筛选条件56
     */
    public String getFxjOut56() {
        return fxjOut56;
    }

    /**
     * 设置无筛选条件56
     *
     * @param fxjOut56 无筛选条件56
     */
    public void setFxjOut56(String fxjOut56) {
        this.fxjOut56 = fxjOut56;
    }

    /**
     * 获取无筛选条件57
     *
     * @return fxj_out_57 - 无筛选条件57
     */
    public String getFxjOut57() {
        return fxjOut57;
    }

    /**
     * 设置无筛选条件57
     *
     * @param fxjOut57 无筛选条件57
     */
    public void setFxjOut57(String fxjOut57) {
        this.fxjOut57 = fxjOut57;
    }

    /**
     * 获取无筛选条件58
     *
     * @return fxj_out_58 - 无筛选条件58
     */
    public String getFxjOut58() {
        return fxjOut58;
    }

    /**
     * 设置无筛选条件58
     *
     * @param fxjOut58 无筛选条件58
     */
    public void setFxjOut58(String fxjOut58) {
        this.fxjOut58 = fxjOut58;
    }


    @Override
    public String toString() {
        return "FxjFBiData01{" +
                "id='" + id + '\'' +
                ", createName='" + createName + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateName='" + updateName + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                ", sysOrgCode='" + sysOrgCode + '\'' +
                ", sysCompanyCode='" + sysCompanyCode + '\'' +
                ", bpmStatus='" + bpmStatus + '\'' +
                ", fxjOut01='" + fxjOut01 + '\'' +
                ", fxjOut02='" + fxjOut02 + '\'' +
                ", fxjOut03='" + fxjOut03 + '\'' +
                ", fxjOut04='" + fxjOut04 + '\'' +
                ", fxjOut05='" + fxjOut05 + '\'' +
                ", fxjOut06='" + fxjOut06 + '\'' +
                ", fxjOut07='" + fxjOut07 + '\'' +
                ", fxjOut08='" + fxjOut08 + '\'' +
                ", fxjOut09='" + fxjOut09 + '\'' +
                ", fxjOut10='" + fxjOut10 + '\'' +
                ", fxjOut11='" + fxjOut11 + '\'' +
                ", fxjOut12='" + fxjOut12 + '\'' +
                ", fxjOut13='" + fxjOut13 + '\'' +
                ", fxjOut14='" + fxjOut14 + '\'' +
                ", fxjOut15='" + fxjOut15 + '\'' +
                ", fxjOut16='" + fxjOut16 + '\'' +
                ", fxjOut17='" + fxjOut17 + '\'' +
                ", fxjOut18='" + fxjOut18 + '\'' +
                ", fxjOut19='" + fxjOut19 + '\'' +
                ", fxjOut20='" + fxjOut20 + '\'' +
                ", fxjOut62='" + fxjOut62 + '\'' +
                ", fxjOut63='" + fxjOut63 + '\'' +
                ", fxjOut64='" + fxjOut64 + '\'' +
                ", fxjOut21='" + fxjOut21 + '\'' +
                ", fxjOut65='" + fxjOut65 + '\'' +
                ", fxjOut60='" + fxjOut60 + '\'' +
                ", fxjOut61='" + fxjOut61 + '\'' +
                ", fxjOut26='" + fxjOut26 + '\'' +
                ", fxjOut27='" + fxjOut27 + '\'' +
                ", fxjOut28='" + fxjOut28 + '\'' +
                ", fxjOut29='" + fxjOut29 + '\'' +
                ", fxjOut22='" + fxjOut22 + '\'' +
                ", fxjOut66='" + fxjOut66 + '\'' +
                ", fxjOut23='" + fxjOut23 + '\'' +
                ", fxjOut67='" + fxjOut67 + '\'' +
                ", fxjOut24='" + fxjOut24 + '\'' +
                ", fxjOut68='" + fxjOut68 + '\'' +
                ", fxjOut25='" + fxjOut25 + '\'' +
                ", fxjOut69='" + fxjOut69 + '\'' +
                ", fxjOut73='" + fxjOut73 + '\'' +
                ", fxjOut30='" + fxjOut30 + '\'' +
                ", fxjOut74='" + fxjOut74 + '\'' +
                ", fxjOut31='" + fxjOut31 + '\'' +
                ", fxjOut75='" + fxjOut75 + '\'' +
                ", fxjOut32='" + fxjOut32 + '\'' +
                ", fxjOut76='" + fxjOut76 + '\'' +
                ", fxjOut70='" + fxjOut70 + '\'' +
                ", fxjOut71='" + fxjOut71 + '\'' +
                ", fxjOut72='" + fxjOut72 + '\'' +
                ", fxjOut37='" + fxjOut37 + '\'' +
                ", fxjOut38='" + fxjOut38 + '\'' +
                ", fxjOut39='" + fxjOut39 + '\'' +
                ", fxjOut33='" + fxjOut33 + '\'' +
                ", fxjOut77='" + fxjOut77 + '\'' +
                ", fxjOut34='" + fxjOut34 + '\'' +
                ", fxjOut78='" + fxjOut78 + '\'' +
                ", fxjOut35='" + fxjOut35 + '\'' +
                ", fxjOut79='" + fxjOut79 + '\'' +
                ", fxjOut36='" + fxjOut36 + '\'' +
                ", fxjOut40='" + fxjOut40 + '\'' +
                ", fxjOut41='" + fxjOut41 + '\'' +
                ", fxjOut42='" + fxjOut42 + '\'' +
                ", fxjOut43='" + fxjOut43 + '\'' +
                ", fxjOut80='" + fxjOut80 + '\'' +
                ", fxjOut48='" + fxjOut48 + '\'' +
                ", fxjOut49='" + fxjOut49 + '\'' +
                ", fxjOut44='" + fxjOut44 + '\'' +
                ", fxjOut45='" + fxjOut45 + '\'' +
                ", fxjOut46='" + fxjOut46 + '\'' +
                ", fxjOut47='" + fxjOut47 + '\'' +
                ", fxjOut51='" + fxjOut51 + '\'' +
                ", fxjOut52='" + fxjOut52 + '\'' +
                ", fxjOut53='" + fxjOut53 + '\'' +
                ", fxjOut54='" + fxjOut54 + '\'' +
                ", fxjOut50='" + fxjOut50 + '\'' +
                ", fxjOut59='" + fxjOut59 + '\'' +
                ", fxjOut55='" + fxjOut55 + '\'' +
                ", fxjOut56='" + fxjOut56 + '\'' +
                ", fxjOut57='" + fxjOut57 + '\'' +
                ", fxjOut58='" + fxjOut58 + '\'' +
                '}';
    }
}