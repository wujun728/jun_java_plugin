/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.baomidou.mybatisplus.annotation.TableId
 *  com.baomidou.mybatisplus.annotation.TableName
 */
package com.jun.plugin.online.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value="online_table_column")
public class OnlineTableColumnEntity {
    private /* synthetic */ boolean queryItem;
    private /* synthetic */ boolean columnNull;
    private /* synthetic */ Integer length;
    private /* synthetic */ boolean columnPk;
    private /* synthetic */ boolean formItem;
    private /* synthetic */ String formInput;
    private /* synthetic */ boolean gridItem;
    private /* synthetic */ String queryInput;
    private   /* synthetic */ int[] EE;
    private /* synthetic */ boolean gridSort;
    private /* synthetic */ Integer sort;
    private /* synthetic */ String defaultValue;
    private /* synthetic */ String formDefault;
    private /* synthetic */ String name;
    private /* synthetic */ String columnType;
    private /* synthetic */ String comments;
    private /* synthetic */ String formDict;
    private /* synthetic */ String queryType;
    private /* synthetic */ Integer pointLength;
    private /* synthetic */ String tableId;
    @TableId
    private /* synthetic */ Long id;
    private /* synthetic */ boolean formRequired;

//    public int hashCode() {
//        int n;
//        int n2;
//        int n3;
//        int n4;
//        int n5;
//        int n6;
//        int n7;
//        int n8;
//        int n9;
//        int n10;
//        int n11;
//        int n12;
//        int n13;
//        int n14;
//        int n15;
//        int n16;
//        int n17;
//        int n18;
//        int n19;
//        int n20;
//        int n21;
//        OnlineTableColumnEntity LfTb;
//        int kfTb = EE[2];
//        int JfTb = EE[0];
//        int n22 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.sk(LfTb.isColumnPk() ? 1 : 0)) {
//            n21 = EE[3];
//            "".length();
//            if ("   ".length() < " ".length()) {
//                return (140 + 68 - 49 + 6 ^ 82 + 46 - 58 + 68) & (0xC3 ^ 0xB0 ^ (0x62 ^ 0x3E) ^ -" ".length());
//            }
//        } else {
//            n21 = EE[4];
//        }
//        JfTb = n22 + n21;
//        int n23 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.sk(LfTb.isColumnNull() ? 1 : 0)) {
//            n20 = EE[3];
//            "".length();
//            if ("  ".length() <= " ".length()) {
//                return (0x15 ^ 0x7D ^ (0x19 ^ 0x39)) & ("  ".length() & ~"  ".length() ^ (0x57 ^ 0x1F) ^ -" ".length());
//            }
//        } else {
//            n20 = EE[4];
//        }
//        JfTb = n23 + n20;
//        int n24 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.sk(LfTb.isFormItem() ? 1 : 0)) {
//            n19 = EE[3];
//            "".length();
//            if ("  ".length() == 0) {
//                return (46 + 27 - -63 + 86 ^ 80 + 108 - 158 + 122) & (103 + 231 - 176 + 81 ^ 152 + 159 - 246 + 104 ^ -" ".length());
//            }
//        } else {
//            n19 = EE[4];
//        }
//        JfTb = n24 + n19;
//        int n25 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.sk(LfTb.isFormRequired() ? 1 : 0)) {
//            n18 = EE[3];
//            "".length();
//            if ((0x87 ^ 0x83) != (0xC0 ^ 0xC4)) {
//                return (0x35 ^ 0x68) & ~(0x4D ^ 0x10);
//            }
//        } else {
//            n18 = EE[4];
//        }
//        JfTb = n25 + n18;
//        int n26 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.sk(LfTb.isGridItem() ? 1 : 0)) {
//            n17 = EE[3];
//            "".length();
//            if (-" ".length() != -" ".length()) {
//                return (0x5C ^ 2) & ~(0xD2 ^ 0x8C);
//            }
//        } else {
//            n17 = EE[4];
//        }
//        JfTb = n26 + n17;
//        int n27 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.sk(LfTb.isGridSort() ? 1 : 0)) {
//            n16 = EE[3];
//            "".length();
//            if (" ".length() == 0) {
//                return (0xBB ^ 0xAE) & ~(0xD2 ^ 0xC7);
//            }
//        } else {
//            n16 = EE[4];
//        }
//        JfTb = n27 + n16;
//        int n28 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.sk(LfTb.isQueryItem() ? 1 : 0)) {
//            n15 = EE[3];
//            "".length();
//            if ("  ".length() <= " ".length()) {
//                return (5 ^ 0x41 ^ (0x53 ^ 0x20)) & (184 + 230 - 394 + 223 ^ 74 + 31 - 104 + 195 ^ -" ".length());
//            }
//        } else {
//            n15 = EE[4];
//        }
//        JfTb = n28 + n15;
//        Long hfTb = LfTb.getId();
//        int n29 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(hfTb)) {
//            n14 = EE[5];
//            "".length();
//            if ("   ".length() == 0) {
//                return (7 ^ 0x10) & ~(0x19 ^ 0xE);
//            }
//        } else {
//            n14 = ((Object)hfTb).hashCode();
//        }
//        JfTb = n29 + n14;
//        Integer GfTb = LfTb.getLength();
//        int n30 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(GfTb)) {
//            n13 = EE[5];
//            "".length();
//            if (" ".length() > " ".length()) {
//                return (0xF5 ^ 0xA0) & ~(0x26 ^ 0x73);
//            }
//        } else {
//            n13 = ((Object)GfTb).hashCode();
//        }
//        JfTb = n30 + n13;
//        Integer ffTb = LfTb.getPointLength();
//        int n31 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(ffTb)) {
//            n12 = EE[5];
//            "".length();
//            if ("  ".length() != "  ".length()) {
//                return (0x45 ^ 0x6B) & ~(0x6E ^ 0x40);
//            }
//        } else {
//            n12 = ((Object)ffTb).hashCode();
//        }
//        JfTb = n31 + n12;
//        Integer EfTb = LfTb.getSort();
//        int n32 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(EfTb)) {
//            n11 = EE[5];
//            "".length();
//            if ("  ".length() >= (0x64 ^ 0x60)) {
//                return (0x82 ^ 0x89) & ~(0x71 ^ 0x7A);
//            }
//        } else {
//            n11 = ((Object)EfTb).hashCode();
//        }
//        JfTb = n32 + n11;
//        String dfTb = LfTb.getName();
//        int n33 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(dfTb)) {
//            n10 = EE[5];
//            "".length();
//            if ("  ".length() == (0x14 ^ 0x10)) {
//                return (0xBF ^ 0x86) & ~(0x43 ^ 0x7A);
//            }
//        } else {
//            n10 = dfTb.hashCode();
//        }
//        JfTb = n33 + n10;
//        String CfTb = LfTb.getComments();
//        int n34 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(CfTb)) {
//            n9 = EE[5];
//            "".length();
//            if ("   ".length() != "   ".length()) {
//                return (0x58 ^ 0x48 ^ (0x73 ^ 0x7D)) & (43 + 65 - 33 + 92 ^ 2 + 124 - -22 + 37 ^ -" ".length());
//            }
//        } else {
//            n9 = CfTb.hashCode();
//        }
//        JfTb = n34 + n9;
//        String bfTb = LfTb.getDefaultValue();
//        int n35 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(bfTb)) {
//            n8 = EE[5];
//            "".length();
//            if ("  ".length() < 0) {
//                return (0x57 ^ 0x7E ^ (0x79 ^ 0x66)) & (0xCE ^ 0xC2 ^ (0xA5 ^ 0x9F) ^ -" ".length());
//            }
//        } else {
//            n8 = bfTb.hashCode();
//        }
//        JfTb = n35 + n8;
//        String AfTb = LfTb.getColumnType();
//        int n36 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(AfTb)) {
//            n7 = EE[5];
//            "".length();
//            if (null != null) {
//                return (0x12 ^ 0x18 ^ (0x3A ^ 0x79)) & (0x50 ^ 0x6D ^ (0x62 ^ 0x16) ^ -" ".length());
//            }
//        } else {
//            n7 = AfTb.hashCode();
//        }
//        JfTb = n36 + n7;
//        String ZETb = LfTb.getFormInput();
//        int n37 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(ZETb)) {
//            n6 = EE[5];
//            "".length();
//            if (-"   ".length() > 0) {
//                return (97 + 55 - 6 + 48 ^ 79 + 5 - -28 + 24) & (9 + 36 - -85 + 72 ^ 71 + 25 - 83 + 115 ^ -" ".length());
//            }
//        } else {
//            n6 = ZETb.hashCode();
//        }
//        JfTb = n37 + n6;
//        String yETb = LfTb.getFormDefault();
//        int n38 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(yETb)) {
//            n5 = EE[5];
//            "".length();
//            if (((129 + 116 - 167 + 95 ^ 22 + 80 - 0 + 38) & (0xB6 ^ 0x82 ^ (0xA2 ^ 0xB7) ^ -" ".length())) != ((0x34 ^ 0x78 ^ (0x96 ^ 0x8D)) & (0x65 ^ 0x55 ^ (0x38 ^ 0x5F) ^ -" ".length()))) {
//                return (0x54 ^ 0x14 ^ (0xFD ^ 0x8E)) & (0x6F ^ 0x1E ^ (0x26 ^ 0x64) ^ -" ".length());
//            }
//        } else {
//            n5 = yETb.hashCode();
//        }
//        JfTb = n38 + n5;
//        String XETb = LfTb.getFormDict();
//        int n39 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(XETb)) {
//            n4 = EE[5];
//            "".length();
//            if (-" ".length() > 0) {
//                return (0xB9 ^ 0x97) & ~(0x80 ^ 0xAE);
//            }
//        } else {
//            n4 = XETb.hashCode();
//        }
//        JfTb = n39 + n4;
//        String wETb = LfTb.getQueryType();
//        int n40 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(wETb)) {
//            n3 = EE[5];
//            "".length();
//            if (null != null) {
//                return (0xA1 ^ 0xB6 ^ (0x8A ^ 0x9B)) & (0x34 ^ 0x65 ^ (0x2F ^ 0x78) ^ -" ".length());
//            }
//        } else {
//            n3 = wETb.hashCode();
//        }
//        JfTb = n40 + n3;
//        String VETb = LfTb.getQueryInput();
//        int n41 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(VETb)) {
//            n2 = EE[5];
//            "".length();
//            if (null != null) {
//                return (0x4F ^ 0x44) & ~(0x46 ^ 0x4D);
//            }
//        } else {
//            n2 = VETb.hashCode();
//        }
//        JfTb = n41 + n2;
//        String uETb = LfTb.getTableId();
//        int n42 = JfTb * EE[2];
//        if (OnlineTableColumnEntity.Pk(uETb)) {
//            n = EE[5];
//            "".length();
//            if ("   ".length() <= ((0x57 ^ 0x6C) & ~(0xB6 ^ 0x8D))) {
//                return (0x8C ^ 0xB3) & ~(0x84 ^ 0xBB);
//            }
//        } else {
//            n = uETb.hashCode();
//        }
//        JfTb = n42 + n;
//        return JfTb;
//    }
//
//    public void setTableId(String ELTb) {
//        dLTb.tableId = ELTb;
//    }
//
//    public void setQueryItem(boolean hmTb) {
//        JmTb.queryItem = hmTb;
//    }
//
//    protected boolean canEqual(Object dGTb) {
//        return dGTb instanceof OnlineTableColumnEntity;
//    }
//
//    public void setColumnNull(boolean moTb) {
//        NoTb.columnNull = moTb;
//    }
//
//    public void setLength(Integer wPTb) {
//        XPTb.length = wPTb;
//    }
//
//    public String getName() {
//        OnlineTableColumnEntity GTTb;
//        return GTTb.name;
//    }
//
//    public void setName(String NqTb) {
//        mqTb.name = NqTb;
//    }
//
//    public void setId(Long uqTb) {
//        TqTb.id = uqTb;
//    }
//
//    private static boolean Pk(Object object) {
//        return object == null;
//    }
//
//    public void setPointLength(Integer PPTb) {
//        sPTb.pointLength = PPTb;
//    }
//
//    public void setFormDict(String bNTb) {
//        ENTb.formDict = bNTb;
//    }
//
//    public String getDefaultValue() {
//        OnlineTableColumnEntity usTb;
//        return usTb.defaultValue;
//    }
//
//    public String getFormDefault() {
//        OnlineTableColumnEntity ZRTb;
//        return ZRTb.formDefault;
//    }
//
//    public String getTableId() {
//        OnlineTableColumnEntity ZqTb;
//        return ZqTb.tableId;
//    }
//
//    public Integer getLength() {
//        OnlineTableColumnEntity ATTb;
//        return ATTb.length;
//    }
//
//    public boolean isGridSort() {
//        OnlineTableColumnEntity PRTb;
//        return PRTb.gridSort;
//    }
//
//    public void setFormItem(boolean GoTb) {
//        hoTb.formItem = GoTb;
//    }
//
//    public String toString() {
//        OnlineTableColumnEntity ZdTb;
//        return "OnlineTableColumnEntity(id=" + ZdTb.getId() + ", name=" + ZdTb.getName() + ", comments=" + ZdTb.getComments() + ", length=" + ZdTb.getLength() + ", pointLength=" + ZdTb.getPointLength() + ", defaultValue=" + ZdTb.getDefaultValue() + ", columnType=" + ZdTb.getColumnType() + ", columnPk=" + ZdTb.isColumnPk() + ", columnNull=" + ZdTb.isColumnNull() + ", formItem=" + ZdTb.isFormItem() + ", formRequired=" + ZdTb.isFormRequired() + ", formInput=" + ZdTb.getFormInput() + ", formDefault=" + ZdTb.getFormDefault() + ", formDict=" + ZdTb.getFormDict() + ", gridItem=" + ZdTb.isGridItem() + ", gridSort=" + ZdTb.isGridSort() + ", queryItem=" + ZdTb.isQueryItem() + ", queryType=" + ZdTb.getQueryType() + ", queryInput=" + ZdTb.getQueryInput() + ", sort=" + ZdTb.getSort() + ", tableId=" + ZdTb.getTableId() + ")";
//    }
//
//    public void setFormInput(String qNTb) {
//        TNTb.formInput = qNTb;
//    }
//
//    public void setColumnPk(boolean ToTb) {
//        uoTb.columnPk = ToTb;
//    }
//
//    public String getComments() {
//        OnlineTableColumnEntity ETTb;
//        return ETTb.comments;
//    }
//
//    private static boolean qk(Object object) {
//        return object != null;
//    }
//
//    public void setFormRequired(boolean XNTb) {
//        AoTb.formRequired = XNTb;
//    }
//
//    public boolean isFormRequired() {
//        OnlineTableColumnEntity fsTb;
//        return fsTb.formRequired;
//    }
//
//    public String getQueryType() {
//        OnlineTableColumnEntity JRTb;
//        return JRTb.queryType;
//    }
//
//    public String getFormDict() {
//        OnlineTableColumnEntity wRTb;
//        return wRTb.formDict;
//    }
//
//    public boolean isFormItem() {
//        OnlineTableColumnEntity JsTb;
//        return JsTb.formItem;
//    }
//
//    public Integer getSort() {
//        OnlineTableColumnEntity CRTb;
//        return CRTb.sort;
//    }
//
//    public String getQueryInput() {
//        OnlineTableColumnEntity fRTb;
//        return fRTb.queryInput;
//    }
//
//    public void setComments(String fqTb) {
//        GqTb.comments = fqTb;
//    }
//
//    public Long getId() {
//        OnlineTableColumnEntity kTTb;
//        return kTTb.id;
//    }
//
//    public boolean equals(Object NhTb) {
//        block120: {
//            block121: {
//                String PhTb;
//                String qhTb;
//                block119: {
//                    OnlineTableColumnEntity sJTb;
//                    OnlineTableColumnEntity uJTb;
//                    block117: {
//                        block118: {
//                            String RhTb;
//                            String shTb;
//                            block116: {
//                                block114: {
//                                    block115: {
//                                        String ThTb;
//                                        String uhTb;
//                                        block113: {
//                                            block111: {
//                                                block112: {
//                                                    String VhTb;
//                                                    String whTb;
//                                                    block110: {
//                                                        block108: {
//                                                            block109: {
//                                                                String XhTb;
//                                                                String yhTb;
//                                                                block107: {
//                                                                    block105: {
//                                                                        block106: {
//                                                                            String ZhTb;
//                                                                            String AJTb;
//                                                                            block104: {
//                                                                                block102: {
//                                                                                    block103: {
//                                                                                        String bJTb;
//                                                                                        String CJTb;
//                                                                                        block101: {
//                                                                                            block99: {
//                                                                                                block100: {
//                                                                                                    String dJTb;
//                                                                                                    String EJTb;
//                                                                                                    block98: {
//                                                                                                        block96: {
//                                                                                                            block97: {
//                                                                                                                String fJTb;
//                                                                                                                String GJTb;
//                                                                                                                block95: {
//                                                                                                                    block93: {
//                                                                                                                        block94: {
//                                                                                                                            String hJTb;
//                                                                                                                            String JJTb;
//                                                                                                                            block92: {
//                                                                                                                                block90: {
//                                                                                                                                    block91: {
//                                                                                                                                        Integer kJTb;
//                                                                                                                                        Integer LJTb;
//                                                                                                                                        block89: {
//                                                                                                                                            block87: {
//                                                                                                                                                block88: {
//                                                                                                                                                    Integer mJTb;
//                                                                                                                                                    Integer NJTb;
//                                                                                                                                                    block86: {
//                                                                                                                                                        block84: {
//                                                                                                                                                            block85: {
//                                                                                                                                                                Integer oJTb;
//                                                                                                                                                                Integer PJTb;
//                                                                                                                                                                block83: {
//                                                                                                                                                                    block81: {
//                                                                                                                                                                        block82: {
//                                                                                                                                                                            Long qJTb;
//                                                                                                                                                                            Long RJTb;
//                                                                                                                                                                            block80: {
//                                                                                                                                                                                if (OnlineTableColumnEntity.mk(NhTb, uJTb)) {
//                                                                                                                                                                                    return EE[0];
//                                                                                                                                                                                }
//                                                                                                                                                                                if (OnlineTableColumnEntity.Nk(NhTb instanceof OnlineTableColumnEntity)) {
//                                                                                                                                                                                    return EE[1];
//                                                                                                                                                                                }
//                                                                                                                                                                                sJTb = (OnlineTableColumnEntity)NhTb;
//                                                                                                                                                                                if (OnlineTableColumnEntity.Nk(sJTb.canEqual(uJTb) ? 1 : 0)) {
//                                                                                                                                                                                    return EE[1];
//                                                                                                                                                                                }
//                                                                                                                                                                                if (OnlineTableColumnEntity.ok(uJTb.isColumnPk() ? 1 : 0, sJTb.isColumnPk() ? 1 : 0)) {
//                                                                                                                                                                                    return EE[1];
//                                                                                                                                                                                }
//                                                                                                                                                                                if (OnlineTableColumnEntity.ok(uJTb.isColumnNull() ? 1 : 0, sJTb.isColumnNull() ? 1 : 0)) {
//                                                                                                                                                                                    return EE[1];
//                                                                                                                                                                                }
//                                                                                                                                                                                if (OnlineTableColumnEntity.ok(uJTb.isFormItem() ? 1 : 0, sJTb.isFormItem() ? 1 : 0)) {
//                                                                                                                                                                                    return EE[1];
//                                                                                                                                                                                }
//                                                                                                                                                                                if (OnlineTableColumnEntity.ok(uJTb.isFormRequired() ? 1 : 0, sJTb.isFormRequired() ? 1 : 0)) {
//                                                                                                                                                                                    return EE[1];
//                                                                                                                                                                                }
//                                                                                                                                                                                if (OnlineTableColumnEntity.ok(uJTb.isGridItem() ? 1 : 0, sJTb.isGridItem() ? 1 : 0)) {
//                                                                                                                                                                                    return EE[1];
//                                                                                                                                                                                }
//                                                                                                                                                                                if (OnlineTableColumnEntity.ok(uJTb.isGridSort() ? 1 : 0, sJTb.isGridSort() ? 1 : 0)) {
//                                                                                                                                                                                    return EE[1];
//                                                                                                                                                                                }
//                                                                                                                                                                                if (OnlineTableColumnEntity.ok(uJTb.isQueryItem() ? 1 : 0, sJTb.isQueryItem() ? 1 : 0)) {
//                                                                                                                                                                                    return EE[1];
//                                                                                                                                                                                }
//                                                                                                                                                                                RJTb = uJTb.getId();
//                                                                                                                                                                                qJTb = sJTb.getId();
//                                                                                                                                                                                if (!OnlineTableColumnEntity.Pk(RJTb)) break block80;
//                                                                                                                                                                                if (!OnlineTableColumnEntity.qk(qJTb)) break block81;
//                                                                                                                                                                                "".length();
//                                                                                                                                                                                if (-(36 + 108 - 99 + 103 ^ 5 + 50 - -40 + 49) > 0) {
//                                                                                                                                                                                    return ((187 + 94 - 187 + 145 ^ 113 + 49 - 151 + 181) & (0x2C ^ 0x7D ^ (0xC7 ^ 0xB9) ^ -" ".length())) != 0;
//                                                                                                                                                                                }
//                                                                                                                                                                                break block82;
//                                                                                                                                                                            }
//                                                                                                                                                                            if (!OnlineTableColumnEntity.Nk(((Object)RJTb).equals(qJTb) ? 1 : 0)) break block81;
//                                                                                                                                                                        }
//                                                                                                                                                                        return EE[1];
//                                                                                                                                                                    }
//                                                                                                                                                                    PJTb = uJTb.getLength();
//                                                                                                                                                                    oJTb = sJTb.getLength();
//                                                                                                                                                                    if (!OnlineTableColumnEntity.Pk(PJTb)) break block83;
//                                                                                                                                                                    if (!OnlineTableColumnEntity.qk(oJTb)) break block84;
//                                                                                                                                                                    "".length();
//                                                                                                                                                                    if ("  ".length() < "  ".length()) {
//                                                                                                                                                                        return ((0xA1 ^ 0xA6 ^ (0x17 ^ 0x39)) & (0xA ^ 0x4F ^ (0x5A ^ 0x36) ^ -" ".length())) != 0;
//                                                                                                                                                                    }
//                                                                                                                                                                    break block85;
//                                                                                                                                                                }
//                                                                                                                                                                if (!OnlineTableColumnEntity.Nk(((Object)PJTb).equals(oJTb) ? 1 : 0)) break block84;
//                                                                                                                                                            }
//                                                                                                                                                            return EE[1];
//                                                                                                                                                        }
//                                                                                                                                                        NJTb = uJTb.getPointLength();
//                                                                                                                                                        mJTb = sJTb.getPointLength();
//                                                                                                                                                        if (!OnlineTableColumnEntity.Pk(NJTb)) break block86;
//                                                                                                                                                        if (!OnlineTableColumnEntity.qk(mJTb)) break block87;
//                                                                                                                                                        "".length();
//                                                                                                                                                        if (-"  ".length() >= 0) {
//                                                                                                                                                            return ((0xFD ^ 0x8A ^ 76 + 101 - 112 + 62) & (155 + 10 - 11 + 26 ^ 117 + 109 - 94 + 56 ^ -" ".length())) != 0;
//                                                                                                                                                        }
//                                                                                                                                                        break block88;
//                                                                                                                                                    }
//                                                                                                                                                    if (!OnlineTableColumnEntity.Nk(((Object)NJTb).equals(mJTb) ? 1 : 0)) break block87;
//                                                                                                                                                }
//                                                                                                                                                return EE[1];
//                                                                                                                                            }
//                                                                                                                                            LJTb = uJTb.getSort();
//                                                                                                                                            kJTb = sJTb.getSort();
//                                                                                                                                            if (!OnlineTableColumnEntity.Pk(LJTb)) break block89;
//                                                                                                                                            if (!OnlineTableColumnEntity.qk(kJTb)) break block90;
//                                                                                                                                            "".length();
//                                                                                                                                            if ((0x5B ^ 0xC ^ (0x3D ^ 0x6F)) <= 0) {
//                                                                                                                                                return ((0x9A ^ 0x85 ^ (0xBC ^ 0x86)) & (0xFD ^ 0x98 ^ (0xD ^ 0x4D) ^ -" ".length())) != 0;
//                                                                                                                                            }
//                                                                                                                                            break block91;
//                                                                                                                                        }
//                                                                                                                                        if (!OnlineTableColumnEntity.Nk(((Object)LJTb).equals(kJTb) ? 1 : 0)) break block90;
//                                                                                                                                    }
//                                                                                                                                    return EE[1];
//                                                                                                                                }
//                                                                                                                                JJTb = uJTb.getName();
//                                                                                                                                hJTb = sJTb.getName();
//                                                                                                                                if (!OnlineTableColumnEntity.Pk(JJTb)) break block92;
//                                                                                                                                if (!OnlineTableColumnEntity.qk(hJTb)) break block93;
//                                                                                                                                "".length();
//                                                                                                                                if (-" ".length() > 0) {
//                                                                                                                                    return ((0xB3 ^ 0x8C ^ (0xF ^ 0x22)) & (0x3D ^ 0x58 ^ (0xEF ^ 0x98) ^ -" ".length())) != 0;
//                                                                                                                                }
//                                                                                                                                break block94;
//                                                                                                                            }
//                                                                                                                            if (!OnlineTableColumnEntity.Nk(JJTb.equals(hJTb) ? 1 : 0)) break block93;
//                                                                                                                        }
//                                                                                                                        return EE[1];
//                                                                                                                    }
//                                                                                                                    GJTb = uJTb.getComments();
//                                                                                                                    fJTb = sJTb.getComments();
//                                                                                                                    if (!OnlineTableColumnEntity.Pk(GJTb)) break block95;
//                                                                                                                    if (!OnlineTableColumnEntity.qk(fJTb)) break block96;
//                                                                                                                    "".length();
//                                                                                                                    if (null != null) {
//                                                                                                                        return ((0xD ^ 0x1F) & ~(0x69 ^ 0x7B)) != 0;
//                                                                                                                    }
//                                                                                                                    break block97;
//                                                                                                                }
//                                                                                                                if (!OnlineTableColumnEntity.Nk(GJTb.equals(fJTb) ? 1 : 0)) break block96;
//                                                                                                            }
//                                                                                                            return EE[1];
//                                                                                                        }
//                                                                                                        EJTb = uJTb.getDefaultValue();
//                                                                                                        dJTb = sJTb.getDefaultValue();
//                                                                                                        if (!OnlineTableColumnEntity.Pk(EJTb)) break block98;
//                                                                                                        if (!OnlineTableColumnEntity.qk(dJTb)) break block99;
//                                                                                                        "".length();
//                                                                                                        if (((0x6C ^ 0x40 ^ (0xB ^ 0x66)) & (183 + 51 - 200 + 190 ^ 74 + 51 - 17 + 53 ^ -" ".length())) != 0) {
//                                                                                                            return ((0xC ^ 0x50 ^ (0x4D ^ 0x1A)) & (7 ^ 0x48 ^ (0x2C ^ 0x68) ^ -" ".length())) != 0;
//                                                                                                        }
//                                                                                                        break block100;
//                                                                                                    }
//                                                                                                    if (!OnlineTableColumnEntity.Nk(EJTb.equals(dJTb) ? 1 : 0)) break block99;
//                                                                                                }
//                                                                                                return EE[1];
//                                                                                            }
//                                                                                            CJTb = uJTb.getColumnType();
//                                                                                            bJTb = sJTb.getColumnType();
//                                                                                            if (!OnlineTableColumnEntity.Pk(CJTb)) break block101;
//                                                                                            if (!OnlineTableColumnEntity.qk(bJTb)) break block102;
//                                                                                            "".length();
//                                                                                            if (((104 + 35 - 57 + 117 ^ 90 + 95 - 98 + 59) & (0xD0 ^ 0xC7 ^ (0x19 ^ 0x5B) ^ -" ".length())) != 0) {
//                                                                                                return ((0x28 ^ 0x2D ^ (0xE4 ^ 0xC7)) & (54 + 50 - 82 + 111 ^ 62 + 15 - -62 + 24 ^ -" ".length())) != 0;
//                                                                                            }
//                                                                                            break block103;
//                                                                                        }
//                                                                                        if (!OnlineTableColumnEntity.Nk(CJTb.equals(bJTb) ? 1 : 0)) break block102;
//                                                                                    }
//                                                                                    return EE[1];
//                                                                                }
//                                                                                AJTb = uJTb.getFormInput();
//                                                                                ZhTb = sJTb.getFormInput();
//                                                                                if (!OnlineTableColumnEntity.Pk(AJTb)) break block104;
//                                                                                if (!OnlineTableColumnEntity.qk(ZhTb)) break block105;
//                                                                                "".length();
//                                                                                if ("  ".length() == "   ".length()) {
//                                                                                    return ((0x61 ^ 0x1A ^ (0x32 ^ 0x65)) & (0xF1 ^ 0x86 ^ (0x33 ^ 0x68) ^ -" ".length())) != 0;
//                                                                                }
//                                                                                break block106;
//                                                                            }
//                                                                            if (!OnlineTableColumnEntity.Nk(AJTb.equals(ZhTb) ? 1 : 0)) break block105;
//                                                                        }
//                                                                        return EE[1];
//                                                                    }
//                                                                    yhTb = uJTb.getFormDefault();
//                                                                    XhTb = sJTb.getFormDefault();
//                                                                    if (!OnlineTableColumnEntity.Pk(yhTb)) break block107;
//                                                                    if (!OnlineTableColumnEntity.qk(XhTb)) break block108;
//                                                                    "".length();
//                                                                    if ("  ".length() <= -" ".length()) {
//                                                                        return ((0x6B ^ 0x62) & ~(0x44 ^ 0x4D)) != 0;
//                                                                    }
//                                                                    break block109;
//                                                                }
//                                                                if (!OnlineTableColumnEntity.Nk(yhTb.equals(XhTb) ? 1 : 0)) break block108;
//                                                            }
//                                                            return EE[1];
//                                                        }
//                                                        whTb = uJTb.getFormDict();
//                                                        VhTb = sJTb.getFormDict();
//                                                        if (!OnlineTableColumnEntity.Pk(whTb)) break block110;
//                                                        if (!OnlineTableColumnEntity.qk(VhTb)) break block111;
//                                                        "".length();
//                                                        if ("  ".length() <= -" ".length()) {
//                                                            return ((0x2D ^ 0x22 ^ (0xFA ^ 0xB3)) & (0x4C ^ 0x69 ^ (0x7D ^ 0x1E) ^ -" ".length())) != 0;
//                                                        }
//                                                        break block112;
//                                                    }
//                                                    if (!OnlineTableColumnEntity.Nk(whTb.equals(VhTb) ? 1 : 0)) break block111;
//                                                }
//                                                return EE[1];
//                                            }
//                                            uhTb = uJTb.getQueryType();
//                                            ThTb = sJTb.getQueryType();
//                                            if (!OnlineTableColumnEntity.Pk(uhTb)) break block113;
//                                            if (!OnlineTableColumnEntity.qk(ThTb)) break block114;
//                                            "".length();
//                                            if (-("   ".length() ^ (0x3E ^ 0x38)) >= 0) {
//                                                return ((0x1F ^ 0x65 ^ (0x2C ^ 0x5C)) & (0x65 ^ 0x14 ^ (0xCC ^ 0xB7) ^ -" ".length())) != 0;
//                                            }
//                                            break block115;
//                                        }
//                                        if (!OnlineTableColumnEntity.Nk(uhTb.equals(ThTb) ? 1 : 0)) break block114;
//                                    }
//                                    return EE[1];
//                                }
//                                shTb = uJTb.getQueryInput();
//                                RhTb = sJTb.getQueryInput();
//                                if (!OnlineTableColumnEntity.Pk(shTb)) break block116;
//                                if (!OnlineTableColumnEntity.qk(RhTb)) break block117;
//                                "".length();
//                                if ("  ".length() < 0) {
//                                    return ((26 + 217 - 106 + 113 ^ 61 + 98 - 93 + 96) & (0x98 ^ 0x8F ^ (0x69 ^ 0x26) ^ -" ".length())) != 0;
//                                }
//                                break block118;
//                            }
//                            if (!OnlineTableColumnEntity.Nk(shTb.equals(RhTb) ? 1 : 0)) break block117;
//                        }
//                        return EE[1];
//                    }
//                    qhTb = uJTb.getTableId();
//                    PhTb = sJTb.getTableId();
//                    if (!OnlineTableColumnEntity.Pk(qhTb)) break block119;
//                    if (!OnlineTableColumnEntity.qk(PhTb)) break block120;
//                    "".length();
//                    if (((0xF ^ 0x4F) & ~(0x33 ^ 0x73)) != ((0x42 ^ 0x51) & ~(3 ^ 0x10))) {
//                        return ((0x82 ^ 0x98) & ~(0x45 ^ 0x5F)) != 0;
//                    }
//                    break block121;
//                }
//                if (!OnlineTableColumnEntity.Nk(qhTb.equals(PhTb) ? 1 : 0)) break block120;
//            }
//            return EE[1];
//        }
//        return EE[0];
//    }
//
//    public void setQueryType(String yLTb) {
//        ZLTb.queryType = yLTb;
//    }
//
//    public boolean isQueryItem() {
//        OnlineTableColumnEntity NRTb;
//        return NRTb.queryItem;
//    }
//
//    public String getColumnType() {
//        OnlineTableColumnEntity RsTb;
//        return RsTb.columnType;
//    }
//
//    public void setGridSort(boolean PmTb) {
//        omTb.gridSort = PmTb;
//    }
//
//    public void setDefaultValue(String kPTb) {
//        LPTb.defaultValue = kPTb;
//    }
//
//    public OnlineTableColumnEntity() {
//        OnlineTableColumnEntity NTTb;
//    }
//
//    public Integer getPointLength() {
//        OnlineTableColumnEntity XsTb;
//        return XsTb.pointLength;
//    }
//
//    public void setColumnType(String APTb) {
//        bPTb.columnType = APTb;
//    }
//
//    private static boolean Nk(int n) {
//        return n == 0;
//    }
//
//    static {
//        OnlineTableColumnEntity.kk();
//    }
//
//    public void setQueryInput(String RLTb) {
//        sLTb.queryInput = RLTb;
//    }
//
//    public void setSort(Integer kLTb) {
//        NLTb.sort = kLTb;
//    }
//
//    public boolean isColumnPk() {
//        OnlineTableColumnEntity PsTb;
//        return PsTb.columnPk;
//    }
//
//    public void setGridItem(boolean wmTb) {
//        VmTb.gridItem = wmTb;
//    }
//
//    private static boolean sk(int n) {
//        return n != 0;
//    }
//
//    public boolean isColumnNull() {
//        OnlineTableColumnEntity LsTb;
//        return LsTb.columnNull;
//    }
//
//    public boolean isGridItem() {
//        OnlineTableColumnEntity TRTb;
//        return TRTb.gridItem;
//    }
//
//    public String getFormInput() {
//        OnlineTableColumnEntity CsTb;
//        return CsTb.formInput;
//    }
//
//    private static boolean ok(int n, int n2) {
//        return n != n2;
//    }
//
//    private static void kk() {
//        EE = new int[6];
//        OnlineTableColumnEntity.EE[0] = " ".length();
//        OnlineTableColumnEntity.EE[1] = (122 + 96 - 162 + 89 ^ 130 + 6 - 100 + 163) & (0x6F ^ 9 ^ (0x18 ^ 0x28) ^ -" ".length());
//        OnlineTableColumnEntity.EE[2] = 112 + 104 - 137 + 103 ^ 27 + 11 - 0 + 103;
//        OnlineTableColumnEntity.EE[3] = 3 ^ 0x4C;
//        OnlineTableColumnEntity.EE[4] = 0xAC ^ 0xA5 ^ (0x28 ^ 0x40);
//        OnlineTableColumnEntity.EE[5] = 0x71 ^ 0x5A;
//    }
//
//    private static boolean mk(Object object, Object object2) {
//        return object == object2;
//    }
//
//    public void setFormDefault(String LNTb) {
//        kNTb.formDefault = LNTb;
//    }
}

